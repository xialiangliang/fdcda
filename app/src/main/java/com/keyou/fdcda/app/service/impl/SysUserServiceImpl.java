package com.keyou.fdcda.app.service.impl;

import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SysUserService;
import com.keyou.fdcda.api.utils.Assert;
import com.keyou.fdcda.api.utils.EncodeUtil;
import com.keyou.fdcda.api.utils.Result;
import com.keyou.fdcda.api.utils.StringUtil;
import com.keyou.fdcda.app.dao.SysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
    private final static Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findById(Long id) {
        return sysUserMapper.findById(id);
    }

    @Override
    public Long update(SysUser entity) {
        return sysUserMapper.update(entity);
    }

    @Override
    public void deleteById(Long id) {
        sysUserMapper.deleteById(id);
    }

    @Override
    public Integer save(SysUser entity) {
        return sysUserMapper.save(entity);
    }

    @Override
    public Long findPageCount(Map<String, Object> map) {
        return sysUserMapper.findPageCount(map);
    }

    @Override
    public PageResult<SysUser> findPage(PaginationQuery query) {
        List<SysUser> list = new ArrayList<>();
        Long count = sysUserMapper.findPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = sysUserMapper.findPage(query.getQueryData());
        }
        return new PageResult<SysUser>(list, count.intValue(), query);
    }

    @Override
    public List<SysUser> findAllPage(Map<String, Object> map) {
        return sysUserMapper.findAllPage(map);
    }
    
    @Override
    public SysUser getUserByPhone(String phone) {
        return sysUserMapper.getUserByPhone(phone);
    }

    @Override
    public Result<SysUser> validateNewUser(SysUser user) throws Exception {
        if (StringUtil.isBlank(user.getPhone())) {
            return new Result<>(null, "手机号不能为空！", -1, false);
        }
        if (StringUtil.isBlank(user.getUsername())) {
            return new Result<>(null, "姓名不能为空！", -1, false);
        }
        if (StringUtil.isBlank(user.getLoginname())) {
            return new Result<>(null, "登录名不能为空！", -1, false);
        }
        if (EncodeUtil.md5("").equals(user.getLoginpwd())) {
            return new Result<>(null, "密码不能为空！", -1, false);
        }
        SysUser sysUser = sysUserMapper.getUserByLoginname(user.getLoginname());
        if (sysUser != null) {
            return new Result<>(null, "登录名已存在！", -1, false);
        }
        if (StringUtil.isBlank(user.getPhone())) {
            return new Result<>(null, "手机号不能为空！", -1, false);
        }
        if (!user.getLoginpwd().equals(user.getLoginpwdConfirm())) {
            return new Result<>(null, "两次密码不相同！", -1, false);
        }
        user.setCreateTime(new Date());
        user.setModifyTime(new Date());
        user.setValid(1);
        return new Result<>(user, "", 0, true);
    }
}
