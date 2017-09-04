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

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
        String validateCode = request.getParameter("validateCode");
        String validateCode2 = (String) request.getSession().getAttribute(Constants.SESSION_LOGIN_VALIDATE_CODE);
        if (!(validateCode2 != null && validateCode2.equals(validateCode))) {
            map.put(Constants.MESSAGE, "验证码错误");
            return map;
        }
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

    @RequestMapping(value = "/getSalt")
    @ResponseBody
    public Map<String, Object> getSalt(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String phone = request.getParameter("phone");
        SysUser sysUser;
        if (StringUtil.isPhone(phone)) {
            sysUser = sysUserService.getUserByPhone(phone);
            map.put("salt", sysUser.getLoginpwd().split("\\" + Constants.PASSWORD_SALT_SPLIT)[1]);
        } else if (StringUtil.isLoginname(phone)) {
            sysUser = sysUserService.getUserByLoginname(phone);
            map.put("salt", sysUser.getLoginpwd().split("\\" + Constants.PASSWORD_SALT_SPLIT)[1]);
        }
        return map;
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute(Constants.SESSION_USER);
        return "redirect:/login";
    }

    @RequestMapping(value = "/validateCode")
    public void validateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int width = 63;
        int height = 37;
        Random random = new Random();
        //设置response头信息
        //禁止缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        //生成缓冲区image类
        BufferedImage image = new BufferedImage(width, height, 1);
        //产生image类的Graphics用于绘制操作
        Graphics g = image.getGraphics();
        //Graphics类的样式
        g.setColor(this.getRandColor(220, 250));
        g.setFont(new Font("Times New Roman",0,22));
        g.fillRect(0, 0, width, height);
        //绘制干扰线
        for(int i=0;i<80;i++){
            g.setColor(this.getRandColor(130, 200));
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(12);
            int y1 = random.nextInt(12);
            g.drawLine(x, y, x + x1, y + y1);
        }

        //绘制字符
        String strCode = "";
        for(int i=0;i<4;i++){
            String rand = String.valueOf(random.nextInt(10));
            strCode = strCode + rand;
//            g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
            g.setColor(this.getRandColor(80, 150));
            g.drawString(rand, 13*i+6, 28);
        }
        //将字符保存到session中用于前端的验证
        request.getSession().setAttribute(Constants.SESSION_LOGIN_VALIDATE_CODE, strCode);
        g.dispose();

        ImageIO.write(image, "JPEG", response.getOutputStream());
        response.getOutputStream().flush();
    }

    private Color getRandColor(int fc,int bc){
        Random random = new Random();
        if(fc>255)
            fc = 255;
        if(bc>255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r,g,b);
    }
}
