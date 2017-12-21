package com.keyou.fdcda.app.service.impl;

import com.keyou.fdcda.api.model.OrderEvaluate;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.OrderEvaluateService;
import com.keyou.fdcda.app.dao.OrderEvaluateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("orderEvaluateService")
public class OrderEvaluateServiceImpl implements OrderEvaluateService {
    private final static Logger logger = LoggerFactory.getLogger(OrderEvaluateServiceImpl.class);

    @Autowired
    private OrderEvaluateMapper orderEvaluateMapper;

    @Override
    public OrderEvaluate findById(Long id) {
        return orderEvaluateMapper.findById(id);
    }

    @Override
    public Long update(OrderEvaluate entity) {
        return orderEvaluateMapper.update(entity);
    }

    @Override
    public void deleteById(Long id) {
        orderEvaluateMapper.deleteById(id);
    }

    @Override
    public Integer save(OrderEvaluate entity) {
        return orderEvaluateMapper.save(entity);
    }

    @Override
    public Long findPageCount(Map<String, Object> map) {
        return orderEvaluateMapper.findPageCount(map);
    }

    @Override
    public PageResult<OrderEvaluate> findPage(PaginationQuery query) {
        List<OrderEvaluate> list = new ArrayList<>();
        Long count = orderEvaluateMapper.findPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = orderEvaluateMapper.findPage(query.getQueryData());
        }
        return new PageResult<>(list, count.intValue(), query);
    }

    @Override
    public List<OrderEvaluate> findAllPage(Map<String, Object> map) {
        return orderEvaluateMapper.findAllPage(map);
    }
}
