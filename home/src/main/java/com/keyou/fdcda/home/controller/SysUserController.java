package com.keyou.fdcda.home.controller;

import com.keyou.fdcda.api.constants.Constants;
import com.keyou.fdcda.api.model.SysResource;
import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.RedisService;
import com.keyou.fdcda.api.service.SysManagerService;
import com.keyou.fdcda.api.utils.config.UrlConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzq on 2017-08-26.
 */
@Controller
@RequestMapping("/sys_user")
public class SysUserController {
    private final static Logger logger = LoggerFactory.getLogger(SysUserController.class);
    @Autowired
    private RedisService redisService;
    @Autowired
    private UrlConfig urlConfig;
    @Autowired
    private SysManagerService sysManagerService;


    @RequestMapping
    public String index(HttpServletRequest request, Model model, PaginationQuery query) {
        SysUser user = (SysUser) request.getSession().getAttribute(Constants.SESSION_USER);
        List<SysResource> topResource = sysManagerService.getTopResource(user.getId());
        model.addAttribute("topResource", topResource);
        PageResult<SysUser> userPage = sysManagerService.findUserPage(query);
        model.addAttribute("page", userPage);
        return "/page/sys_user";
    }
}
