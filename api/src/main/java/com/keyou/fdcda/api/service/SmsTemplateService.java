package com.keyou.fdcda.api.service;

import com.keyou.fdcda.api.model.SmsTemplate;
import com.keyou.fdcda.api.service.base.BaseService;


public interface SmsTemplateService extends BaseService<SmsTemplate, Long> {
    SmsTemplate findByType(Long type);
}
