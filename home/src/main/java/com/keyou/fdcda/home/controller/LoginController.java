package com.keyou.fdcda.home.controller;

import com.keyou.fdcda.api.constants.Constants;
import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.service.RedisService;
import com.keyou.fdcda.api.service.SysUserService;
import com.keyou.fdcda.api.utils.EncodeUtil;
import com.keyou.fdcda.api.utils.RandomUtil;
import com.keyou.fdcda.api.utils.Result;
import com.keyou.fdcda.api.utils.StringUtil;
import com.keyou.fdcda.api.utils.config.UrlConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzq on 2017-07-08.
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private RedisService redisService;
    @Autowired
    private UrlConfig urlConfig;
    @Autowired
    private SysUserService sysUserService;


    @RequestMapping
    public String index(HttpServletRequest request, Model model) {
        try {
            String token = RandomUtil.produceString(32);
            request.getSession().setAttribute(Constants.SESSION_LOGIN_TOKEN, token);
            model.addAttribute("token", token);
            
        } catch (Exception e) {
            logger.error("服务异常", e);
        }
        return "/page/login";
    }
    
//    @RequestMapping(value = "/index", method = RequestMethod.POST)
    @RequestMapping(value = "/confirm")
    @ResponseBody
    public Map<String, Object> confirm(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.SUCCESS, false);
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        if (StringUtil.isNotBlank(phone)) {
            String token = (String) request.getSession().getAttribute(Constants.SESSION_LOGIN_TOKEN);
            request.getSession().removeAttribute(Constants.SESSION_LOGIN_TOKEN);
            try {
                Result<SysUser> result;
                if (StringUtil.isPhone(phone)) {
                    result = sysUserService.loginByPhone(phone, password, token);
                } else if (StringUtil.isLoginname(phone)) {
                    result = sysUserService.loginByLoginname(phone, password, token);
                } else {
                    result = new Result<>(null, "用户不存在！", -1, false);
                }
                if (!result.getSuccess()) {
                    map.put(Constants.MESSAGE, result.getMessage());
                    return map;
                }
                request.getSession().setAttribute(Constants.SESSION_USER, result.getData());
            } catch (Exception e) {
                logger.error("服务异常", e);
                map.put(Constants.MESSAGE, "服务异常");
                return map;
            }
        }
        map.put(Constants.SUCCESS, true);
        map.put(Constants.MESSAGE, "登录成功");
        return map;
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute(Constants.SESSION_USER);
        return "redirect:/login";
    }
}
