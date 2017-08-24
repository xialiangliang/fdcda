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
            if(StringUtil.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
                int index = ip.indexOf(44);
                if(index != -1) {
                    ip.substring(0, index);
                }
            } else {
                ip = req.getHeader("X-Real-IP");
                if(StringUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                    if(StringUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                        ip = req.getHeader("Proxy-Client-IP");
                    }

                    if(StringUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                        ip = req.getHeader("WL-Proxy-Client-IP");
                    }

                    if(StringUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                        ip = req.getHeader("host");
                    }

                    if(StringUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                        ip = req.getRemoteAddr();
                    }
                }
            }
        } finally {
            return StringUtil.substringBefore(StringUtil.substringBefore(ip, ","), ":");
        }
    }
}
