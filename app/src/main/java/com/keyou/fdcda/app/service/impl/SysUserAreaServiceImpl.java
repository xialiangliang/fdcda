package com.keyou.fdcda.app.service.impl;

import com.keyou.fdcda.api.model.SysUserArea;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SysUserAreaService;
import com.keyou.fdcda.app.dao.SysUserAreaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysUserAreaService")
public class SysUserAreaServiceImpl implements SysUserAreaService {
    private final static Logger logger = LoggerFactory.getLogger(SysUserAreaServiceImpl.class);

    @Autowired
    private SysUserAreaMapper sysUserAreaMapper;

    @Override
    public SysUserArea findById(Long id) {
        return sysUserAreaMapper.findById(id);
    }

    @Override
    public Long update(SysUserArea entity) {
        return sysUserAreaMapper.update(entity);
    }

    @Override
    public void deleteById(Long id) {
        sysUserAreaMapper.deleteById(id);
    }

    @Override
    public Integer save(SysUserArea entity) {
        return sysUserAreaMapper.save(entity);
    }

    @Override
    public Long findPageCount(Map<String, Object> map) {
        return sysUserAreaMapper.findPageCount(map);
    }

    @Override
    public PageResult<SysUserArea> findPage(PaginationQuery query) {
        List<SysUserArea> list = new ArrayList<>();
        Long count = sysUserAreaMapper.findPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = sysUserAreaMapper.findPage(query.getQueryData());
        }
        return new PageResult<>(list, count.intValue(), query);
    }

    @Override
    public List<SysUserArea> findAllPage(Map<String, Object> map) {
        return sysUserAreaMapper.findAllPage(map);
    }
}
