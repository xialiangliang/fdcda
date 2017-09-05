package com.keyou.fdcda.api.service;

import com.keyou.fdcda.api.model.SysUserrole;
import com.keyou.fdcda.api.service.base.BaseService;

import java.util.List;
import java.util.Map;


public interface SysUserroleService extends BaseService<SysUserrole, Long> {
    List<SysUserrole> findAllPageWithRoleName(Map<String, Object> map);
    
    void updateRolesData(Long userId, List<Long> roleIds);
}
