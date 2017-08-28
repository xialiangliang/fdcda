package com.keyou.fdcda.api.service;

import com.keyou.fdcda.api.model.SysResource;
import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;

import java.util.List;
import java.util.Map;

/**
 * Created by zzq on 2017-07-08.
 */
public interface SysManagerService {

    List<SysResource> getTopResource(Long userId);

    SysUser getUserByPhone(String phone);
    
    PageResult<SysUser> findUserPage(PaginationQuery query);
}
