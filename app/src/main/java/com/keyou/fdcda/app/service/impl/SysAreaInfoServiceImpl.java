package com.keyou.fdcda.app.service.impl;

import com.keyou.fdcda.api.model.SysAreaInfo;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SysAreaInfoService;
import com.keyou.fdcda.app.dao.SysAreaInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysAreaInfoService")
public class SysAreaInfoServiceImpl implements SysAreaInfoService {
    private final static Logger logger = LoggerFactory.getLogger(SysAreaInfoServiceImpl.class);

    @Autowired
    private SysAreaInfoMapper sysAreaInfoMapper;

    @Override
    public SysAreaInfo findById(Long id) {
        return sysAreaInfoMapper.findById(id);
    }

    @Override
    public Long update(SysAreaInfo entity) {
        return sysAreaInfoMapper.update(entity);
    }

    @Override
    public void deleteById(Long id) {
        sysAreaInfoMapper.deleteById(id);
    }

    @Override
    public Integer save(SysAreaInfo entity) {
        return sysAreaInfoMapper.save(entity);
    }

    @Override
    public Long findPageCount(Map<String, Object> map) {
        return sysAreaInfoMapper.findPageCount(map);
    }

    @Override
    public PageResult<SysAreaInfo> findPage(PaginationQuery query) {
        List<SysAreaInfo> list = new ArrayList<>();
        Long count = sysAreaInfoMapper.findPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = sysAreaInfoMapper.findPage(query.getQueryData());
        }
        return new PageResult<>(list, count.intValue(), query);
    }

    @Override
    public List<SysAreaInfo> findAllPage(Map<String, Object> map) {
        return sysAreaInfoMapper.findAllPage(map);
    }
}
