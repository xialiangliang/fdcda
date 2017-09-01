package com.keyou.fdcda.app.service.impl;

import com.keyou.fdcda.api.constants.SmsConstants;
import com.keyou.fdcda.api.model.SmsTemplate;
import com.keyou.fdcda.api.service.SmsService;
import com.keyou.fdcda.api.service.SmsTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("smsService")
public class SmsServiceImpl implements SmsService {
    private final static Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);
    
    @Autowired
    private SmsTemplateService smsTemplateService;

    @Override
    public void sendSms(String phone, String content) {
        this.sendSmsRaw(phone, content);
    }

    @Override
    public void sendSms(String phone, SmsConstants smsConstants, String[] params) {
        SmsTemplate smsTemplate = smsTemplateService.findByType(smsConstants.getType());
        String content = String.format(smsTemplate.getTemplate(), (Object[]) params);
        this.sendSmsRaw(phone, content);
    }
    
    private void sendSmsRaw(String phone, String content) {
        // TODO Send SMS
        logger.info("SmsSend:phone=[{}], content=[{}]", phone, content);
    }
}
