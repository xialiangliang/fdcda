package com.keyou.fdcda.home.interceptor;

import com.keyou.fdcda.api.model.SysResource;
import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.service.RedisService;
import com.keyou.fdcda.api.utils.HttpUtil;
import com.keyou.fdcda.api.utils.StringUtil;
import com.keyou.fdcda.api.utils.config.UrlConfig;
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
import java.util.List;

/**
 * Created by zzq on 2017/8/24.
 */
public class ContextInterceptor extends HandlerInterceptorAdapter {
    
    private static final Logger logger = LoggerFactory.getLogger(ContextInterceptor.class);

    private static ThreadLocal<Long> processStartTimeVariable = new ThreadLocal();
    public long reqSlowTime;
    private String userIdSessionKey;
    
    @Autowired
    private RedisService redisService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        processStartTimeVariable.set(System.currentTimeMillis());
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Object userName = session.getAttribute(this.getUserIdSessionKey());
        MDC.put("_sessionId", session.getId());
        MDC.put("_ip", HttpUtil.getIpAddr(request));
        MDC.put("userId", null == userName?"":userName.toString());
        String uri = request.getRequestURI().replace(request.getContextPath(), "");
        if(!(handler instanceof HandlerMethod)) {
            return true;
        } else {
            HandlerMethod hm = (HandlerMethod)handler;
            logger.info("EXEC:{}.{},{} URI:{}", hm.getBeanType().getName(), hm.getMethod().getName(), request.getMethod(), uri);
            return true;
        }
    }


    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) throws Exception {
        if(null == mav) {
            this.processTime(request);
        } else {
            String viewName = mav.getViewName();
            logger.info("{} to location :{}", StringUtil.startsWith(viewName, "redirect:")?"redirect":"forward", viewName);
        }
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        this.processTime(request);
    }

    private void processTime(HttpServletRequest req) {
        Long start = processStartTimeVariable.get();
        if(null != start) {
            processStartTimeVariable.remove();
            long processTime = System.currentTimeMillis() - start;
            String uri = req.getRequestURI().replace(req.getContextPath(), "");
            if(processTime > this.getReqSlowTime()) {
                logger.warn("process in {} : {} mills", uri, processTime);
            } else {
                logger.info("process in {} : {} mills", uri, processTime);
            }

        }
    }

    private long getReqSlowTime() {
        if(0L == this.reqSlowTime) {
            this.reqSlowTime = 1500L;
        }

        return this.reqSlowTime;
    }

    public void setReqSlowTime(long reqSlowTime) {
        this.reqSlowTime = reqSlowTime;
    }

    public String getUserIdSessionKey() {
        if(StringUtil.isBlank(this.userIdSessionKey)) {
            this.userIdSessionKey = "userId";
        }

        return this.userIdSessionKey;
    }

    public void setUserNameSessionKey(String userNameSessionKey) {
        this.userIdSessionKey = userNameSessionKey;
    }
}
