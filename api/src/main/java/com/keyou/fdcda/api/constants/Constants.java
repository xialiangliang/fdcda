package com.keyou.fdcda.api.constants;

/**
 * Created by zzq on 2017-08-27.
 */
public class Constants {
    public static final String SUCCESS = "success";
    public static final String MESSAGE = "message";
    public static final String REDIRECT = "redirect";
    
    public static final String URL_LOGIN = "/login";
    public static final String URL_LOGIN_CONFIRM = "/login/confirm";
    public static final String URL_INDEX = "/";
    
    public static final String SESSION_USER = "session_user";
    public static final String SESSION_PRIVATE_KEY = "session_private_key";
    public static final String SESSION_LOGIN_TOKEN = "session_login_token";
    
    public static final String MD5 = "md5";
    public static final String DEFAULT_PASSWROD = "123456";

    /*********************** regex **********************/
    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{1,20}$";
    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_PHONE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)|(^\\d{17}[a-z]$)";
}
