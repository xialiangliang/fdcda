package com.keyou.fdcda.api.service;

import com.keyou.fdcda.api.model.CustomerInfo;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.base.BaseService;


public interface CustomerInfoService extends BaseService<CustomerInfo, Long> {
    PageResult<CustomerInfo> findSystemBlackPage(PaginationQuery query);
}
