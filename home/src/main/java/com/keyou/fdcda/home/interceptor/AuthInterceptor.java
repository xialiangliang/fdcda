package com.keyou.fdcda.home.interceptor;

import com.keyou.fdcda.api.constants.Constants;
import com.keyou.fdcda.api.constants.RedisConstants;
import com.keyou.fdcda.api.model.SysResource;
import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.model.SysUserrole;
import com.keyou.fdcda.api.service.RedisService;
import com.keyou.fdcda.api.service.SysResourceService;
import com.keyou.fdcda.api.service.SysRoleinfoService;
import com.keyou.fdcda.api.service.SysUserroleService;
import com.keyou.fdcda.api.utils.HttpUtil;
import com.keyou.fdcda.api.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Boolean.*;

/**
 * Created by zzq on 2017/8/24.
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    private static ThreadLocal<Long> processStartTimeVariable = new ThreadLocal();
    
    @Autowired
    private RedisService redisService;
    @Autowired
    private SysResourceService sysResourceService;
    @Autowired
    private SysUserroleService sysUserroleService;
    @Autowired
    private SysRoleinfoService sysRoleinfoService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String uri = request.getRequestURI().replace(request.getContextPath(), "");
        SysUser user = (SysUser) session.getAttribute(Constants.SESSION_USER);
        if (user == null || user.getId() <= 0) {
            if (!(Constants.URL_NO_AUTH_LIST.contains(uri))) {
                response.sendRedirect(request.getContextPath() + Constants.URL_LOGIN);
                return false;
            }
        } else {
            if (Constants.URL_NO_AUTH_LIST.contains(uri)) {
                response.sendRedirect(request.getContextPath() + Constants.URL_INDEX);
                return true;
            } else {
                if (Constants.URL_LOGOUT.equals(uri)) {
                    return true;
                }
                return authCheck(uri, user.getId());
            }
        }
        return true;
    }


    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) throws Exception {
        String uri = request.getRequestURI().replace(request.getContextPath(), "");
        HttpSession session = request.getSession();
        SysUser user = (SysUser) session.getAttribute(Constants.SESSION_USER);
        if (user != null && user.getId() > 0 && mav != null) {
            mav.addObject("user", user);
            redisService.set("111", user, 5000);
            List<SysResource> topResource = sysResourceService.getTopResource(user.getId());
            mav.addObject("topResource", topResource);

            Long current_top_id = (Long) redisService.get("resource_parent_id_" + uri, Long.class);
            Long current_sub_id = (Long) redisService.get("resource_sub_id_" + uri, Long.class);
            if (current_top_id == null) {
                boolean end = false;
                for (SysResource sysResource : topResource) {
                    for (SysResource subSysResource : sysResource.getSubResource()) {
                        if (subSysResource.getUrl().equals(uri)) {
                            current_top_id = subSysResource.getParentId();
                            current_sub_id = subSysResource.getId();
                            redisService.set("resource_parent_id_" + uri, current_top_id);
                            redisService.set("resource_sub_id_" + uri, current_sub_id);
                            end = true;
                            break;
                        }
                    }
                    if (end) {
                        break;
                    }
                }
            }
            // 默认选中第一个标签
            if (current_top_id == null && !topResource.isEmpty()) {
                current_top_id = topResource.get(0).getId();
            }
            mav.addObject("current_top_id", current_top_id);
            mav.addObject("current_sub_id", current_sub_id);
        }
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
    
    private Boolean authCheck(String uri, Long userId) {
        Map<String, Object> query = new HashMap<>();
        query.put("userId", userId.toString());
        List<SysUserrole> userroleList = sysUserroleService.findAllPage(query);
        List<SysResource> resourceList = sysResourceService.findByUrl(uri);
        List<Long> userRoleIdList = userroleList.stream().mapToLong(SysUserrole::getRoleId).boxed().collect(Collectors.toList());
        for (SysResource sysResource : resourceList) {
            if (!redisService.exists(RedisConstants.RESOURCE_ROLE + sysResource.getId())) {
                sysResourceService.findListWithRole();
            }
            String[] roleIdStr = ((String) redisService.get(RedisConstants.RESOURCE_ROLE + sysResource.getId(), String.class)).split(",");
            List<Long> roleList = Arrays.asList(roleIdStr).stream().mapToLong(Long::valueOf).boxed().collect(Collectors.toList());
            roleList.retainAll(userRoleIdList);
            if (!roleList.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
