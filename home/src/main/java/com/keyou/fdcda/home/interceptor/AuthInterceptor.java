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
import com.keyou.fdcda.api.utils.DateUtil;
import com.keyou.fdcda.api.utils.HttpUtil;
import com.keyou.fdcda.api.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
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
        String ss = request.getRequestURI();
        String uri = request.getRequestURI().replace(request.getContextPath(), "");
        SysUser user = (SysUser) session.getAttribute(Constants.SESSION_USER);
        
        if (uri.equalsIgnoreCase("/")) {
            return true;
        }
        
        if (user == null || user.getId() <= 0) {
            if (Constants.URL_AUTH_ALL_LIST.contains(uri)) {
                return true;
            }
            if (!(Constants.URL_NO_AUTH_LIST.contains(uri))) {
                response.sendRedirect(request.getContextPath() + Constants.URL_LOGIN);
                return false;
            }
        } else {
            List<SysResource> resourceList = sysResourceService.findByUrl(uri);
            if (!CollectionUtils.isEmpty(resourceList)) {
                SysResource sysResource = resourceList.get(0);
                if (sysResource.getParentId().equals(0L)) {
                    SysResource topResource = sysResourceService.getTopResource(user.getId(), sysResource.getTopId());
                    if (!CollectionUtils.isEmpty(topResource.getSubResource())) {
                        response.sendRedirect(request.getContextPath() + topResource.getSubResource().get(0).getUrl());
                        return false;
                    }
                }
            }
            if (Constants.URL_AUTH_ALL_LIST.contains(uri)) {
                return true;
            }
            if (Constants.URL_NO_AUTH_LIST.contains(uri)) {
                response.sendRedirect(request.getContextPath() + Constants.URL_INDEX);
                return true;
            } else {
                if (Constants.URL_LOGOUT.equals(uri)) {
                    return true;
                }
                if (Constants.URL_NO_AUTH_LIST2.contains(uri)) {
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
            // 重定向请求不返回model数据
            if (mav.getViewName().startsWith("redirect:")) {
                return;
            }
            mav.addObject("user", user);

            Long current_top_id = 0L;
            Long current_sub_id = 0L;

            List<SysResource> resourceList = sysResourceService.findByUrl(uri);
            if (resourceList.size() <= 0) {
                return;
            }
            SysResource sysResource = resourceList.get(0);
            
            current_top_id = sysResource.getTopId();
            if (sysResource.getType().equals(1)) {
                current_sub_id = sysResource.getId();
            } else {
                current_sub_id = sysResource.getParentId();
            }
            
            SysResource topResource = sysResourceService.getTopResource(user.getId(), sysResource.getTopId());
            List<SysResource> topResourceList = sysResourceService.getTopResourceList(user.getId());
            mav.addObject("topResource", topResource);
            mav.addObject("topResourceList", topResourceList);
            // 默认选中第一个标签
            if (current_top_id == null) {
                current_top_id = 0L;
            }
            // 当前位置
            int cnt = 5;
            SysResource resource = sysResource;
            String currentPosStr = resource.getName();
            while (true) {
                if (resource.getParentId() == null || resource.getParentId() <= 0) {
                    break;
                }
                resource = sysResourceService.findById(resource.getParentId());
                String urlStr = resource.getName();
                if (resource.getType().equals(1)) {
                    urlStr = "<a href=\"" + resource.getUrl() + "\">" + resource.getName() + "</a>";
                }
                currentPosStr = urlStr + " / " + currentPosStr;
                cnt--;
                if (cnt < 0) {
                    break;
                }
            }
            mav.addObject("currentPosStr", currentPosStr);
            mav.addObject("dateStr", DateUtil.getDate("yyyy-MM-dd"));
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
