package com.keyou.fdcda.api.service;

import com.keyou.fdcda.api.model.SysResource;
import com.keyou.fdcda.api.service.base.BaseService;

import java.util.List;


public interface SysResourceService extends BaseService<SysResource, Long> {

    List<SysResource> getTopResource(Long userId);
}
