package com.keyou.fdcda.app.service.impl;

import com.keyou.fdcda.api.model.SysDevice;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SysDeviceService;
import com.keyou.fdcda.app.dao.SysDeviceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysDeviceService")
public class SysDeviceServiceImpl implements SysDeviceService {
    private final static Logger logger = LoggerFactory.getLogger(SysDeviceServiceImpl.class);

    @Autowired
    private SysDeviceMapper sysDeviceMapper;

    @Override
    public SysDevice findById(Long id) {
        return sysDeviceMapper.findById(id);
    }

    @Override
    public Long update(SysDevice entity) {
        return sysDeviceMapper.update(entity);
    }

    @Override
    public void deleteById(Long id) {
        sysDeviceMapper.deleteById(id);
    }

    @Override
    public Integer save(SysDevice entity) {
        return sysDeviceMapper.save(entity);
    }

    @Override
    public Long findPageCount(Map<String, Object> map) {
        return sysDeviceMapper.findPageCount(map);
    }

    @Override
    public PageResult<SysDevice> findPage(PaginationQuery query) {
        List<SysDevice> list = new ArrayList<>();
        Long count = sysDeviceMapper.findPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = sysDeviceMapper.findPage(query.getQueryData());
        }
        return new PageResult<>(list, count.intValue(), query);
    }

    @Override
    public List<SysDevice> findAllPage(Map<String, Object> map) {
        return sysDeviceMapper.findAllPage(map);
    }
}
