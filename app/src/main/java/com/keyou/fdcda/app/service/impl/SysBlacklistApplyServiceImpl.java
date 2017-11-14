package com.keyou.fdcda.app.service.impl;

import com.keyou.fdcda.api.model.SysBlacklistApply;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SysBlacklistApplyService;
import com.keyou.fdcda.app.dao.SysBlacklistApplyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysBlacklistApplyService")
public class SysBlacklistApplyServiceImpl implements SysBlacklistApplyService {
    private final static Logger logger = LoggerFactory.getLogger(SysBlacklistApplyServiceImpl.class);

    @Autowired
    private SysBlacklistApplyMapper sysBlacklistApplyMapper;

    @Override
    public SysBlacklistApply findById(Long id) {
        return sysBlacklistApplyMapper.findById(id);
    }

    @Override
    public Long update(SysBlacklistApply entity) {
        return sysBlacklistApplyMapper.update(entity);
    }

    @Override
    public void deleteById(Long id) {
        sysBlacklistApplyMapper.deleteById(id);
    }

    @Override
    public Integer save(SysBlacklistApply entity) {
        return sysBlacklistApplyMapper.save(entity);
    }

    @Override
    public Long findPageCount(Map<String, Object> map) {
        return sysBlacklistApplyMapper.findPageCount(map);
    }

    @Override
    public PageResult<SysBlacklistApply> findPage(PaginationQuery query) {
        List<SysBlacklistApply> list = new ArrayList<>();
        Long count = sysBlacklistApplyMapper.findPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = sysBlacklistApplyMapper.findPage(query.getQueryData());
        }
        return new PageResult<>(list, count.intValue(), query);
    }

    @Override
    public List<SysBlacklistApply> findAllPage(Map<String, Object> map) {
        return sysBlacklistApplyMapper.findAllPage(map);
    }
}
