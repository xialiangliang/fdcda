package com.keyou.fdcda.app.service.impl;

import com.keyou.fdcda.api.model.SysGoodCategory;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SysGoodCategoryService;
import com.keyou.fdcda.app.dao.SysGoodCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysGoodCategoryService")
public class SysGoodCategoryServiceImpl implements SysGoodCategoryService {
    private final static Logger logger = LoggerFactory.getLogger(SysGoodCategoryServiceImpl.class);

    @Autowired
    private SysGoodCategoryMapper sysGoodCategoryMapper;

    @Override
    public SysGoodCategory findById(Long id) {
        return sysGoodCategoryMapper.findById(id);
    }

    @Override
    public Long update(SysGoodCategory entity) {
        return sysGoodCategoryMapper.update(entity);
    }

    @Override
    public void deleteById(Long id) {
        sysGoodCategoryMapper.deleteById(id);
    }

    @Override
    public Integer save(SysGoodCategory entity) {
        return sysGoodCategoryMapper.save(entity);
    }

    @Override
    public Long findPageCount(Map<String, Object> map) {
        return sysGoodCategoryMapper.findPageCount(map);
    }

    @Override
    public PageResult<SysGoodCategory> findPage(PaginationQuery query) {
        List<SysGoodCategory> list = new ArrayList<>();
        Long count = sysGoodCategoryMapper.findPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = sysGoodCategoryMapper.findPage(query.getQueryData());
        }
        return new PageResult<>(list, count.intValue(), query);
    }

    @Override
    public List<SysGoodCategory> findAllPage(Map<String, Object> map) {
        return sysGoodCategoryMapper.findAllPage(map);
    }
}
