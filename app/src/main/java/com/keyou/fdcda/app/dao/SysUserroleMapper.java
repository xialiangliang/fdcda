package com.keyou.fdcda.app.dao;

import com.keyou.fdcda.app.dao.base.BaseMapper;
import com.keyou.fdcda.api.model.SysUserrole;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysUserroleMapper extends BaseMapper<SysUserrole, java.lang.Long> {

    List<SysUserrole> findAllPageWithRoleName(Map<String, Object> map);
    
    void deleteByRoleId(Map<String, Object> map);
}
