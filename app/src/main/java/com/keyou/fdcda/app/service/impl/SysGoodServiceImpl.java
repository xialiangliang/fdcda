package com.keyou.fdcda.app.service.impl;

import com.keyou.fdcda.api.model.SysGood;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SysGoodService;
import com.keyou.fdcda.app.dao.SysGoodMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysGoodService")
public class SysGoodServiceImpl implements SysGoodService {
    private final static Logger logger = LoggerFactory.getLogger(SysGoodServiceImpl.class);

    @Autowired
    private SysGoodMapper sysGoodMapper;

    @Override
    public SysGood findById(Long id) {
        return sysGoodMapper.findById(id);
    }

    @Override
    public Long update(SysGood entity) {
        return sysGoodMapper.update(entity);
    }

    @Override
    public void deleteById(Long id) {
        sysGoodMapper.deleteById(id);
    }

    @Override
    public Integer save(SysGood entity) {
        return sysGoodMapper.save(entity);
    }

    @Override
    public Long findPageCount(Map<String, Object> map) {
        return sysGoodMapper.findPageCount(map);
    }

    @Override
    public PageResult<SysGood> findPage(PaginationQuery query) {
        List<SysGood> list = new ArrayList<>();
        Long count = sysGoodMapper.findPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = sysGoodMapper.findPage(query.getQueryData());
        }
        return new PageResult<>(list, count.intValue(), query);
    }

    @Override
    public List<SysGood> findAllPage(Map<String, Object> map) {
        return sysGoodMapper.findAllPage(map);
    }
}
