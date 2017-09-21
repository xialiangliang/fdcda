package com.keyou.fdcda.app.service.impl;

import com.keyou.fdcda.api.model.SysGoodOrder;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SysGoodOrderService;
import com.keyou.fdcda.app.dao.SysGoodOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysGoodOrderService")
public class SysGoodOrderServiceImpl implements SysGoodOrderService {
    private final static Logger logger = LoggerFactory.getLogger(SysGoodOrderServiceImpl.class);

    @Autowired
    private SysGoodOrderMapper sysGoodOrderMapper;

    @Override
    public SysGoodOrder findById(Long id) {
        return sysGoodOrderMapper.findById(id);
    }

    @Override
    public Long update(SysGoodOrder entity) {
        return sysGoodOrderMapper.update(entity);
    }

    @Override
    public void deleteById(Long id) {
        sysGoodOrderMapper.deleteById(id);
    }

    @Override
    public Integer save(SysGoodOrder entity) {
        return sysGoodOrderMapper.save(entity);
    }

    @Override
    public Long findPageCount(Map<String, Object> map) {
        return sysGoodOrderMapper.findPageCount(map);
    }

    @Override
    public PageResult<SysGoodOrder> findPage(PaginationQuery query) {
        List<SysGoodOrder> list = new ArrayList<>();
        Long count = sysGoodOrderMapper.findPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = sysGoodOrderMapper.findPage(query.getQueryData());
        }
        return new PageResult<>(list, count.intValue(), query);
    }

    @Override
    public List<SysGoodOrder> findAllPage(Map<String, Object> map) {
        return sysGoodOrderMapper.findAllPage(map);
    }
}
