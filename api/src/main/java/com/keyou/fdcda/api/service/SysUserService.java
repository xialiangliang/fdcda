package com.keyou.fdcda.api.service;

import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.service.base.BaseService;
import com.keyou.fdcda.api.utils.Result;

import javax.servlet.http.HttpServletRequest;


public interface SysUserService extends BaseService<SysUser, Long> {
    SysUser getUserByPhone(String phone);

    SysUser getUserByLoginname(String loginname);
    
    Result<SysUser> validateNewUser(SysUser user) throws Exception;

    Result<SysUser> register(SysUser sysUser);

    Result<SysUser> loginByLoginname(String loginname, String password, String token, HttpServletRequest request);
    
    Result<SysUser> loginByPhone(String phone, String password, String token, HttpServletRequest request);

    Result<SysUser> resetPassword(Long userId);
    
    Result<String> generatSalt();
}
