package com.keyou.fdcda.app.dao;

import com.keyou.fdcda.app.dao.base.BaseMapper;
import com.keyou.fdcda.api.model.CustomerInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CustomerInfoMapper extends BaseMapper<CustomerInfo, Long> {

    Long findSystemBlackPageCount(Map<String, Object> map);

    List<CustomerInfo> findSystemBlackPage(Map<String, Object> map);

    Long findBlackPageCount(Map<String, Object> queryData);

    List<CustomerInfo> findBlackPage(Map<String, Object> queryData);

    List<Long> findRealCustomerIdBySingleId(Long id);

    CustomerInfo findByPhone(String phone);

    Integer saveImageBaseSend(Map<String, Object> map);

    Long updateImageBaseSend(Map<String, Object> map);
}
