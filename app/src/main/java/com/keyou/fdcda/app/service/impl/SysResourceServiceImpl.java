package com.keyou.fdcda.app.service.impl;

import com.keyou.fdcda.api.model.SysResource;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SysResourceService;
import com.keyou.fdcda.app.dao.SysResourceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysResourceService")
public class SysResourceServiceImpl implements SysResourceService {
    private final static Logger logger = LoggerFactory.getLogger(SysResourceServiceImpl.class);

    @Autowired
    private SysResourceMapper sysResourceMapper;

    @Override
    public SysResource findById(Long id) {
        return sysResourceMapper.findById(id);
    }

    @Override
    public Long update(SysResource entity) {
        return sysResourceMapper.update(entity);
    }

    @Override
    public void deleteById(Long id) {
        sysResourceMapper.deleteById(id);
    }

    @Override
    public Integer save(SysResource entity) {
        return sysResourceMapper.save(entity);
    }

    @Override
    public Long findPageCount(Map<String, Object> map) {
        return sysResourceMapper.findPageCount(map);
    }

    @Override
    public PageResult<SysResource> findPage(PaginationQuery query) {
        List<SysResource> list = new ArrayList<>();
        Long count = sysResourceMapper.findPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = sysResourceMapper.findPage(query.getQueryData());
        }
        return new PageResult<>(list, query, count);
    }

    @Override
    public List<SysResource> findAllPage(Map<String, Object> map) {
        return sysResourceMapper.findAllPage(map);
    }
}
