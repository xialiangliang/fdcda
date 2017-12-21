package com.keyou.fdcda.app.service.impl;

import com.keyou.fdcda.api.model.OrderInfo;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.OrderInfoService;
import com.keyou.fdcda.app.dao.OrderInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("orderInfoService")
public class OrderInfoServiceImpl implements OrderInfoService {
    private final static Logger logger = LoggerFactory.getLogger(OrderInfoServiceImpl.class);

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Override
    public OrderInfo findById(Long id) {
        return orderInfoMapper.findById(id);
    }

    @Override
    public Long update(OrderInfo entity) {
        return orderInfoMapper.update(entity);
    }

    @Override
    public void deleteById(Long id) {
        orderInfoMapper.deleteById(id);
    }

    @Override
    public Integer save(OrderInfo entity) {
        return orderInfoMapper.save(entity);
    }

    @Override
    public Long findPageCount(Map<String, Object> map) {
        return orderInfoMapper.findPageCount(map);
    }

    @Override
    public PageResult<OrderInfo> findPage(PaginationQuery query) {
        List<OrderInfo> list = new ArrayList<>();
        Long count = orderInfoMapper.findPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = orderInfoMapper.findPage(query.getQueryData());
        }
        return new PageResult<>(list, count.intValue(), query);
    }

    @Override
    public List<OrderInfo> findAllPage(Map<String, Object> map) {
        return orderInfoMapper.findAllPage(map);
    }
}
