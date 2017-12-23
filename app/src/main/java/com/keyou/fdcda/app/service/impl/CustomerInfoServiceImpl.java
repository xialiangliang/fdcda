package com.keyou.fdcda.app.service.impl;

import com.keyou.fdcda.api.model.CustomerInfo;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.CustomerInfoService;
import com.keyou.fdcda.app.dao.CustomerBlackVipMapper;
import com.keyou.fdcda.app.dao.CustomerInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("customerInfoService")
public class CustomerInfoServiceImpl implements CustomerInfoService {
    private final static Logger logger = LoggerFactory.getLogger(CustomerInfoServiceImpl.class);

    @Autowired
    private CustomerInfoMapper customerInfoMapper;
    
    @Autowired
    private CustomerBlackVipMapper customerBlackVipMapper;

    @Override
    public CustomerInfo findById(Long id) {
        return customerInfoMapper.findById(id);
    }

    @Override
    public Long update(CustomerInfo entity) {
        return customerInfoMapper.update(entity);
    }

    @Override
    public void deleteById(Long id) {
        customerInfoMapper.deleteById(id);
    }

    @Override
    public Integer save(CustomerInfo entity) {
        return customerInfoMapper.save(entity);
    }

    @Override
    public Long findPageCount(Map<String, Object> map) {
        return customerInfoMapper.findPageCount(map);
    }

    @Override
    public PageResult<CustomerInfo> findPage(PaginationQuery query) {
        List<CustomerInfo> list = new ArrayList<>();
        Long count = customerInfoMapper.findPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = customerInfoMapper.findPage(query.getQueryData());
        }
        return new PageResult<>(list, count.intValue(), query);
    }

    @Override
    public List<CustomerInfo> findAllPage(Map<String, Object> map) {
        return customerInfoMapper.findAllPage(map);
    }

    @Override
    public PageResult<CustomerInfo> findSystemBlackPage(PaginationQuery query) {
        List<CustomerInfo> list = new ArrayList<>();
        Long count = customerInfoMapper.findSystemBlackPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = customerInfoMapper.findSystemBlackPage(query.getQueryData());
        }
        return new PageResult<>(list, count.intValue(), query);
    }

    @Override
    public PageResult<CustomerInfo> findBlackPage(PaginationQuery query) {
        List<CustomerInfo> list = new ArrayList<>();
        Long count = customerInfoMapper.findBlackPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = customerInfoMapper.findBlackPage(query.getQueryData());
        }
        return new PageResult<>(list, count.intValue(), query);
    }

    @Override
    public List<Long> findRealCustomerIdBySingleId(Long id) {
        return customerInfoMapper.findRealCustomerIdBySingleId(id);
    }
}
