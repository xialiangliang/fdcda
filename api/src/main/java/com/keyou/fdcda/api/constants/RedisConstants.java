package com.keyou.fdcda.api.constants;

/**
 * Created by zzq on 2017-09-01.
 */
public class RedisConstants {
    public static final String SYS_MODULE = "fdcda_";

    /**
     * 短信密码redis缓存
     */
    public static final String SMS_PWD = SYS_MODULE + "sms_pwd_";


    public static final String RESOURCE_ROLE = SYS_MODULE + "resource_role_";
    
    public static final String LOGIN_VALIDATE_CODE_REQUIRE = SYS_MODULE + "login_validate_code_require_";

    public static final String LOGIN_PWD_ERROR_TIMES = SYS_MODULE + "login_pwd_error_times_";

    public static final String MODIFY_PASSWORD_SALT = SYS_MODULE + "modify_password_salt_";
}
