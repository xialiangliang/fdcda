package com.keyou.fdcda.app.service.impl;

import com.keyou.fdcda.api.model.SysRole;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SysRoleService;
import com.keyou.fdcda.app.dao.SysRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
    private final static Logger logger = LoggerFactory.getLogger(SysRoleServiceImpl.class);

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public SysRole findById(Long id) {
        return sysRoleMapper.findById(id);
    }

    @Override
    public Long update(SysRole entity) {
        return sysRoleMapper.update(entity);
    }

    @Override
    public void deleteById(Long id) {
        sysRoleMapper.deleteById(id);
    }

    @Override
    public Integer save(SysRole entity) {
        return sysRoleMapper.save(entity);
    }

    @Override
    public Long findPageCount(Map<String, Object> map) {
        return sysRoleMapper.findPageCount(map);
    }

    @Override
    public PageResult<SysRole> findPage(PaginationQuery query) {
        List<SysRole> list = new ArrayList<>();
        Long count = sysRoleMapper.findPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = sysRoleMapper.findPage(query.getQueryData());
        }
        return new PageResult<>(list, count.intValue(), query);
    }

    @Override
    public List<SysRole> findAllPage(Map<String, Object> map) {
        return sysRoleMapper.findAllPage(map);
    }
}
