package com.keyou.fdcda.app.dao;

import com.keyou.fdcda.app.dao.base.BaseMapper;
import com.keyou.fdcda.api.model.SysUser;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserMapper extends BaseMapper<SysUser, java.lang.Long> {	

    SysUser getUserByPhone(String phone);

    SysUser getUserByLoginname(String phone);
}
