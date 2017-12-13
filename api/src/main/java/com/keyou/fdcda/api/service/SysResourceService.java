package com.keyou.fdcda.api.service;

import com.keyou.fdcda.api.model.SysResource;
import com.keyou.fdcda.api.service.base.BaseService;
import com.keyou.fdcda.api.utils.Result;

import java.util.List;
import java.util.Map;


public interface SysResourceService extends BaseService<SysResource, Long> {

    List<SysResource> getTopResource(Long userId);

    List<SysResource> getTopResourceList(Long userId);

    SysResource getTopResource(Long userId, Long topResourceId);
    
    Result<List<SysResource>> getTopologicalResource(Map<String, Object> map);
    
    List<SysResource> findByUrl(String url);
    
    List<SysResource> findListWithRole();

    List<Map> getTopologicalResourceJsonData(Map<String, Object> map);
}
