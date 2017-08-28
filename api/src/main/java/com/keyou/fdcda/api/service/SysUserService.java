package com.keyou.fdcda.api.service;

import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.service.base.BaseService;


public interface SysUserService extends BaseService<SysUser, Long> {
    SysUser getUserByPhone(String phone);
}
