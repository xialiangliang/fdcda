package com.keyou.fdcda.app.dao;

import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.app.dao.base.BaseMapper;

public interface SysUserMapper extends BaseMapper<SysUser, Long> {
    SysUser getUserByPhone(String phone);
}