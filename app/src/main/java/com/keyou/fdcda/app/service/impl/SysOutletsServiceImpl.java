package com.keyou.fdcda.app.service.impl;

import com.keyou.fdcda.api.model.SysOutlets;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SysOutletsService;
import com.keyou.fdcda.app.dao.SysOutletsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysOutletsService")
public class SysOutletsServiceImpl implements SysOutletsService {
    private final static Logger logger = LoggerFactory.getLogger(SysOutletsServiceImpl.class);

    @Autowired
    private SysOutletsMapper sysOutletsMapper;

    @Override
    public SysOutlets findById(Long id) {
        return sysOutletsMapper.findById(id);
    }

    @Override
    public Long update(SysOutlets entity) {
        return sysOutletsMapper.update(entity);
    }

    @Override
    public void deleteById(Long id) {
        sysOutletsMapper.deleteById(id);
    }

    @Override
    public Integer save(SysOutlets entity) {
        return sysOutletsMapper.save(entity);
    }

    @Override
    public Long findPageCount(Map<String, Object> map) {
        return sysOutletsMapper.findPageCount(map);
    }

    @Override
    public PageResult<SysOutlets> findPage(PaginationQuery query) {
        List<SysOutlets> list = new ArrayList<>();
        Long count = sysOutletsMapper.findPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = sysOutletsMapper.findPage(query.getQueryData());
        }
        return new PageResult<>(list, count.intValue(), query);
    }

    @Override
    public List<SysOutlets> findAllPage(Map<String, Object> map) {
        return sysOutletsMapper.findAllPage(map);
    }
}
