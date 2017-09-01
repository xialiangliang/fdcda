package com.keyou.fdcda.app.service.impl;

import com.keyou.fdcda.api.model.SmsTemplate;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SmsTemplateService;
import com.keyou.fdcda.app.dao.SmsTemplateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("smsTemplateService")
public class SmsTemplateServiceImpl implements SmsTemplateService {
    private final static Logger logger = LoggerFactory.getLogger(SmsTemplateServiceImpl.class);

    @Autowired
    private SmsTemplateMapper smsTemplateMapper;

    @Override
    public SmsTemplate findById(Long id) {
        return smsTemplateMapper.findById(id);
    }

    @Override
    public Long update(SmsTemplate entity) {
        return smsTemplateMapper.update(entity);
    }

    @Override
    public void deleteById(Long id) {
        smsTemplateMapper.deleteById(id);
    }

    @Override
    public Integer save(SmsTemplate entity) {
        return smsTemplateMapper.save(entity);
    }

    @Override
    public Long findPageCount(Map<String, Object> map) {
        return smsTemplateMapper.findPageCount(map);
    }

    @Override
    public PageResult<SmsTemplate> findPage(PaginationQuery query) {
        List<SmsTemplate> list = new ArrayList<>();
        Long count = smsTemplateMapper.findPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = smsTemplateMapper.findPage(query.getQueryData());
        }
        return new PageResult<>(list, count.intValue(), query);
    }

    @Override
    public List<SmsTemplate> findAllPage(Map<String, Object> map) {
        return smsTemplateMapper.findAllPage(map);
    }

    @Override
    public SmsTemplate findByType(Long type) {
        return smsTemplateMapper.findByType(type);
    }
}
