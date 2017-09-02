package com.keyou.fdcda.api.service;

import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.service.base.BaseService;
import com.keyou.fdcda.api.utils.Result;


public interface SysUserService extends BaseService<SysUser, Long> {
    SysUser getUserByPhone(String phone);
    
    Result<SysUser> validateNewUser(SysUser user) throws Exception;

    Result<SysUser> register(SysUser sysUser);

    Result<SysUser> loginByLoginname(String loginname, String password, String token);
    
    Result<SysUser> loginByPhone(String phone, String password, String token);
}
