package com.keyou.fdcda.app.service.impl;

import com.keyou.fdcda.api.model.SysLoginlog;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SysLoginlogService;
import com.keyou.fdcda.app.dao.SysLoginlogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysLoginlogService")
public class SysLoginlogServiceImpl implements SysLoginlogService {
    private final static Logger logger = LoggerFactory.getLogger(SysLoginlogServiceImpl.class);

    @Autowired
    private SysLoginlogMapper sysLoginlogMapper;

    @Override
    public SysLoginlog findById(Long id) {
        return sysLoginlogMapper.findById(id);
    }

    @Override
    public Long update(SysLoginlog entity) {
        return sysLoginlogMapper.update(entity);
    }

    @Override
    public void deleteById(Long id) {
        sysLoginlogMapper.deleteById(id);
    }

    @Override
    public Integer save(SysLoginlog entity) {
        return sysLoginlogMapper.save(entity);
    }

    @Override
    public Long findPageCount(Map<String, Object> map) {
        return sysLoginlogMapper.findPageCount(map);
    }

    @Override
    public PageResult<SysLoginlog> findPage(PaginationQuery query) {
        List<SysLoginlog> list = new ArrayList<>();
        Long count = sysLoginlogMapper.findPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = sysLoginlogMapper.findPage(query.getQueryData());
        }
        return new PageResult<>(list, count.intValue(), query);
    }

    @Override
    public List<SysLoginlog> findAllPage(Map<String, Object> map) {
        return sysLoginlogMapper.findAllPage(map);
    }
}
