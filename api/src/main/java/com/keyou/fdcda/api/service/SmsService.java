package com.keyou.fdcda.api.service;

import com.keyou.fdcda.api.constants.SmsConstants;

/**
 * Created by zzq on 2017-08-23.
 */
public interface SmsService {

    /**
     * 发送短信
     * @param phone
     * @param content
     */
    void sendSms(String phone, String content);

    /**
     * 发送短信
     * @param phone
     * @param smsConstants 模板
     * @param objects
     */
    void sendSms(String phone, SmsConstants smsConstants, String[] params);
}
