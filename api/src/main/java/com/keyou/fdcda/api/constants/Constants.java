package com.keyou.fdcda.api.constants;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zzq on 2017-08-27.
 */
public class Constants {
    public static final String SUCCESS = "success";
    public static final String MESSAGE = "message";
    public static final String REDIRECT = "redirect";

    public static final List<String> URL_AUTH_ALL_LIST = Arrays.asList(
            "/login/getSalt",
            "/sysUser/getPublicKey");
    
    public static final List<String> URL_NO_AUTH_LIST = Arrays.asList(
            "/login",
            "/login/confirm",
            "/login/getSalt",
            "/login/validateCode");

    public static final List<String> URL_NO_AUTH_LIST2 = Arrays.asList(
            "/modifyPassword",
            "/modifyPassword/confirm");
    public static final String URL_LOGIN = "/login";
    public static final String URL_LOGOUT = "/login/logout";
    public static final String URL_INDEX = "/index";
    
    public static final String SESSION_USER = "session_user";
    public static final String SESSION_PRIVATE_KEY = "session_private_key";
    public static final String SESSION_LOGIN_TOKEN = "session_login_token";
    public static final String SESSION_LOGIN_VALIDATE_CODE = "session_login_validate_code"; // 登录图片验证码
    public static final Integer LOGIN_RETRY_MINUTES = 20;
    
    public static final String HASH_ENCODE = "sha512";
    public static final String PASSWORD_SALT_SPLIT = "$";
    public static final String DEFAULT_PASSWROD = "123456";

    /*********************** regex **********************/
    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{1,20}$";
    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_PHONE = "^1((3[(0-3)(5-9)]|49|5[0-35-9]|66|7[35-8]|8[0-9]|9[89])[0-9]|34[0-8])[0-9]{7}$";
    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)|(^\\d{17}[a-z]$)";
}
