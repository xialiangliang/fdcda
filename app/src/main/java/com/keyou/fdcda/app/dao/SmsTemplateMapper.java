package com.keyou.fdcda.app.dao;

import com.keyou.fdcda.app.dao.base.BaseMapper;
import com.keyou.fdcda.api.model.SmsTemplate;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsTemplateMapper extends BaseMapper<SmsTemplate, Long> {

    SmsTemplate findByType(Long type);
}
