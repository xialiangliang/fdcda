package com.keyou.fdcda.api.utils;

import com.keyou.fdcda.api.model.SysUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zzq on 2017-08-26.
 */
public class SessionUtil {
    
    public static SysUser getUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return (SysUser) request.getAttribute("session_user");
    }
    
    public static void setUser(SysUser sysUser) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.setAttribute("session_user", sysUser);
    }
}
