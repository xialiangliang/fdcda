package com.keyou.fdcda.app.service.impl;

import com.keyou.fdcda.api.model.SysUserrole;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SysUserroleService;
import com.keyou.fdcda.app.dao.SysUserroleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysUserroleService")
public class SysUserroleServiceImpl implements SysUserroleService {
    private final static Logger logger = LoggerFactory.getLogger(SysUserroleServiceImpl.class);

    @Autowired
    private SysUserroleMapper sysUserroleMapper;

    @Override
    public SysUserrole findById(Long id) {
        return sysUserroleMapper.findById(id);
    }

    @Override
    public Long update(SysUserrole entity) {
        return sysUserroleMapper.update(entity);
    }

    @Override
    public void deleteById(Long id) {
        sysUserroleMapper.deleteById(id);
    }

    @Override
    public Integer save(SysUserrole entity) {
        return sysUserroleMapper.save(entity);
    }

    @Override
    public Long findPageCount(Map<String, Object> map) {
        return sysUserroleMapper.findPageCount(map);
    }

    @Override
    public PageResult<SysUserrole> findPage(PaginationQuery query) {
        List<SysUserrole> list = new ArrayList<>();
        Long count = sysUserroleMapper.findPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = sysUserroleMapper.findPage(query.getQueryData());
        }
        return new PageResult<>(list, count.intValue(), query);
    }

    @Override
    public List<SysUserrole> findAllPage(Map<String, Object> map) {
        return sysUserroleMapper.findAllPage(map);
    }
}
