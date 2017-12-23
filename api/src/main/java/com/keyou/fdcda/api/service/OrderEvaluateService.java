package com.keyou.fdcda.api.service;

import com.keyou.fdcda.api.model.OrderEvaluate;
import com.keyou.fdcda.api.service.base.BaseService;

import java.util.List;


public interface OrderEvaluateService extends BaseService<OrderEvaluate, Long> {
    List<OrderEvaluate> findListByCustomerIds(List<Long> ids);
}
