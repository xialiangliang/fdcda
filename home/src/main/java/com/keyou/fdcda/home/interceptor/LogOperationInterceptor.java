package com.keyou.fdcda.home.interceptor;

import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zzq on 2017/8/24.
 */
public class LogOperationInterceptor extends HandlerInterceptorAdapter {

    private static ThreadLocal<Long> processStartTimeVariable = new ThreadLocal();
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        processStartTimeVariable.set(System.currentTimeMillis());
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String paramName = "";
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
        for (MethodParameter methodParameter : methodParameters) {
            if (methodParameter.getParameterName() == null) {
                continue;
            }
            paramName = paramName + methodParameter.getParameterName() + ",";
        }
        if (paramName.length() > 0) {
            paramName = paramName.substring(0, paramName.length() - 1);
        }

        HandlerMethod hm = (HandlerMethod) handler;
        String url = "";
        if (request != null) {
            url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + request.getServletPath();
        }
//        consoleAccountLogService.saveConsoleAccountLog(hm, url, paramName);
        return true;
    }
}
