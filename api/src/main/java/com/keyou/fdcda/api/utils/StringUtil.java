package com.keyou.fdcda.api.utils;

import com.keyou.fdcda.api.constants.Constants;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * Created by zzq on 2017/8/24.
 */
public class StringUtil extends StringUtils {
    
    public static boolean isBlank(final CharSequence cs) {
        return StringUtils.isBlank(cs);
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return StringUtils.isNotBlank(cs);
    }
    
    public static boolean isEmpty(final CharSequence cs) {
        return StringUtils.isEmpty(cs);
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return StringUtils.isNotEmpty(cs);
    }
    
    public static boolean isPhone(String phone) {
        if (phone == null) {
            return false;
        }
        return Pattern.matches(Constants.REGEX_PHONE, phone);
    }

    public static boolean isLoginname(String loginname) {
        if (loginname == null) {
            return false;
        }
        return Pattern.matches(Constants.REGEX_USERNAME, loginname);
    }
    
    public static String removeStr(String src,String rmstr) {
        return src.replace(rmstr, "");
    }
    
    public static String hideName(String name) {
        if (StringUtils.isBlank(name)) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i=0; i<name.length(); i++) {
                if (i == 0) {
                    sb.append(name.charAt(i));
                } else {
                    sb.append("*");
                }
            }
            return sb.toString();
        }
    }
    
    public static void main(String[] args) {
    	String src = "\\mnt\\facepics\\deal\\camera00000000101\\2017-11-24\\20171124100002.jpg";
		System.out.println(src.substring(src.indexOf("deal") ));
	}
}
