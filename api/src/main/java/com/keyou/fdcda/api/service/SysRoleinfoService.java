package com.keyou.fdcda.api.service;

import com.keyou.fdcda.api.model.SysRoleinfo;
import com.keyou.fdcda.api.service.base.BaseService;

import java.util.Map;


public interface SysRoleinfoService extends BaseService<SysRoleinfo, Long> {

    void updateByMap(Long roleId, Map<String, Object> updatedDataMap);
}
