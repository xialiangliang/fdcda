package com.keyou.fdcda.app.dao;

import com.keyou.fdcda.api.model.SysResource;
import com.keyou.fdcda.app.dao.base.BaseMapper;

import java.util.List;
import java.util.Map;

public interface SysResourceMapper extends BaseMapper<SysResource, Long> {
    
    List<Long> findTopResourceId(Long userId);
    List<SysResource> findSubResource(Map<String, Object> map);
    List<SysResource> findByIds(List<Long> ids);
}