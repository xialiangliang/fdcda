package com.keyou.fdcda.app.dao;

import com.keyou.fdcda.app.dao.base.BaseMapper;
import com.keyou.fdcda.api.model.SysResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysResourceMapper extends BaseMapper<SysResource, java.lang.Long> {

    List<Long> findTopResourceId(Long userId);
    List<SysResource> findSubResource(Map<String, Object> map);
    List<SysResource> findByIds(List<Long> ids);

    List<SysResource> findAllPageWithAuth(Map<String, Object> map);
}
