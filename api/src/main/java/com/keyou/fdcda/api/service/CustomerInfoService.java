package com.keyou.fdcda.api.service;

import com.keyou.fdcda.api.model.CustomerInfo;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.base.BaseService;

import java.util.List;


public interface CustomerInfoService extends BaseService<CustomerInfo, Long> {
    PageResult<CustomerInfo> findSystemBlackPage(PaginationQuery query);

    PageResult<CustomerInfo> findBlackPage(PaginationQuery query);

    /**
     * 通过一个采购商id查询所有同一个采购商的id
     * @param id
     * @return
     */
    List<Long> findRealCustomerIdBySingleId(Long id);
}
