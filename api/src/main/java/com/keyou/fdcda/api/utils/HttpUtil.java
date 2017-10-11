package com.keyou.fdcda.api.utils;

import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zzq on 2017/8/24.
 */
public class HttpUtil extends WebUtils {

    public static String getIpAddr(HttpServletRequest req) {
        String ip = req.getHeader("X-Forwarded-For");

        try {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getHeader("x-forwarded-for");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = req.getRemoteAddr();
            }
        } finally {
            String tmp1 = StringUtil.substringBefore(ip, ",");
            long cnt = tmp1.chars().filter(c->c==':').count();
            if (cnt == 1) {
                ip = StringUtil.substringBefore(tmp1, ":");
            } else {
                ip = tmp1;
            }
        }
        return ip;
    }
}
