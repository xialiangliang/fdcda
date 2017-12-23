package com.keyou.fdcda.app.dao;

import com.keyou.fdcda.app.dao.base.BaseMapper;
import com.keyou.fdcda.api.model.OrderEvaluate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderEvaluateMapper extends BaseMapper<OrderEvaluate, Long> {

    List<OrderEvaluate> findListByCustomerIds(List<Long> ids);
}
