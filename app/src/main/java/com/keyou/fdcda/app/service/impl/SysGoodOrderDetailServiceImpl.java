package com.keyou.fdcda.app.service.impl;

import com.keyou.fdcda.api.model.SysGoodOrderDetail;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SysGoodOrderDetailService;
import com.keyou.fdcda.app.dao.SysGoodOrderDetailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysGoodOrderDetailService")
public class SysGoodOrderDetailServiceImpl implements SysGoodOrderDetailService {
    private final static Logger logger = LoggerFactory.getLogger(SysGoodOrderDetailServiceImpl.class);

    @Autowired
    private SysGoodOrderDetailMapper sysGoodOrderDetailMapper;

    @Override
    public SysGoodOrderDetail findById(Long id) {
        return sysGoodOrderDetailMapper.findById(id);
    }

    @Override
    public Long update(SysGoodOrderDetail entity) {
        return sysGoodOrderDetailMapper.update(entity);
    }

    @Override
    public void deleteById(Long id) {
        sysGoodOrderDetailMapper.deleteById(id);
    }

    @Override
    public Integer save(SysGoodOrderDetail entity) {
        return sysGoodOrderDetailMapper.save(entity);
    }

    @Override
    public Long findPageCount(Map<String, Object> map) {
        return sysGoodOrderDetailMapper.findPageCount(map);
    }

    @Override
    public PageResult<SysGoodOrderDetail> findPage(PaginationQuery query) {
        List<SysGoodOrderDetail> list = new ArrayList<>();
        Long count = sysGoodOrderDetailMapper.findPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = sysGoodOrderDetailMapper.findPage(query.getQueryData());
        }
        return new PageResult<>(list, count.intValue(), query);
    }

    @Override
    public List<SysGoodOrderDetail> findAllPage(Map<String, Object> map) {
        return sysGoodOrderDetailMapper.findAllPage(map);
    }
}
