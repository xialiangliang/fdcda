package com.keyou.fdcda.app.service.impl;

import com.keyou.fdcda.api.constants.Constants;
import com.keyou.fdcda.api.constants.SmsConstants;
import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SmsService;
import com.keyou.fdcda.api.service.SysUserService;
import com.keyou.fdcda.api.utils.*;
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
    @Autowired
    private SmsService smsService;

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
    public SysUser getUserByLoginname(String phone) {
        return sysUserMapper.getUserByLoginname(phone);
    }

    @Override
    public Result<SysUser> validateNewUser(SysUser user) throws Exception {
        if (StringUtil.isBlank(user.getPhone())) {
            return new Result<>(null, "手机号不能为空！", -1, false);
        }
        if (!StringUtil.isPhone(user.getPhone())) {
            return new Result<>(null, "手机号不合法！", -1, false);
        }
        if (StringUtil.isBlank(user.getUsername())) {
            return new Result<>(null, "姓名不能为空！", -1, false);
        }
        if (StringUtil.isBlank(user.getLoginname())) {
            return new Result<>(null, "登录名不能为空！", -1, false);
        }
        if (!StringUtil.isLoginname(user.getLoginname())) {
            return new Result<>(null, "登录名不合法！", -1, false);
        }
        SysUser sysUser = sysUserMapper.getUserByLoginname(user.getLoginname());
        if (sysUser != null) {
            return new Result<>(null, "登录名已存在！", -1, false);
        }
        if (StringUtil.isBlank(user.getPhone())) {
            return new Result<>(null, "手机号不能为空！", -1, false);
        }
        sysUser = sysUserMapper.getUserByPhone(user.getPhone());
        if (sysUser != null) {
            return new Result<>(null, "手机号已存在！", -1, false);
        }
        user.setCreateTime(new Date());
        user.setModifyTime(new Date());
        user.setValid(1);
        String password = user.getLoginpwd();
        if (StringUtil.isBlank(password)) {
            // Generate random password
            password = RandomUtil.produceNumber(6);
        }
        user.setLoginpwd(password);
        return new Result<>(user, "", 0, true);
    }

    @Override
    public Result<SysUser> register(SysUser sysUser) {
        // Save user info
        int res = sysUserMapper.save(sysUser);
        if (res == 0) {
            return new Result<>(null, "注册失败，请重试！", -1, false);
        }

        String password = sysUser.getLoginpwd();
        String salt = RandomUtil.produceString(64);
        salt = EncodeUtil.hash(salt, Constants.HASH_ENCODE);
        SysUser vo = new SysUser();
        vo.setId(sysUser.getId());
        vo.setLoginpwd(EncodeUtil.hash(password + salt, Constants.HASH_ENCODE) + Constants.PASSWORD_SALT_SPLIT + salt);
        sysUserMapper.update(vo);
        
        // Send password to user
        smsService.sendSms(sysUser.getPhone(), SmsConstants.REGISTER_SUCCESS,
                new String[]{sysUser.getPhone(), sysUser.getLoginname(), password});
        return new Result<>(sysUser, "注册成功！", 0, true);
    }

    @Override
    public Result<SysUser> loginByLoginname(String loginname, String password, String token) {
        SysUser user = sysUserMapper.getUserByLoginname(loginname);
        if (user == null) {
            return new Result<>(null, "用户不存在", -1, false);
        }
        if (user.getValid() != 1) {
            return new Result<>(null, "无效用户", -1, false);
        }
        String dbPwd = EncodeUtil.hash(user.getLoginpwd().split("\\" + Constants.PASSWORD_SALT_SPLIT)[0] + token, Constants.HASH_ENCODE);
        if (!dbPwd.equals(password)) {
            return new Result<>(null, "密码错误", -1, false);
        }
        return new Result<>(user, "登录成功", 0, true);
    }

    @Override
    public Result<SysUser> loginByPhone(String phone, String password, String token) {
        SysUser user = sysUserMapper.getUserByPhone(phone);
        if (user == null) {
            return new Result<>(null, "用户不存在", -1, false);
        }
        if (user.getValid() != 1) {
            return new Result<>(null, "无效用户", -1, false);
        }
        String dbPwd = EncodeUtil.hash(user.getLoginpwd().split("\\" + Constants.PASSWORD_SALT_SPLIT)[0] + token, Constants.HASH_ENCODE);
        if (!dbPwd.equals(password)) {
            return new Result<>(null, "密码错误", -1, false);
        }
        return new Result<>(user, "登录成功", 0, true);
    }

    @Override
    public Result<SysUser> resetPassword(Long userId) {
        String password = RandomUtil.produceNumber(6);
        String salt = RandomUtil.produceString(64);
        salt = EncodeUtil.hash(salt, Constants.HASH_ENCODE);
        SysUser vo = new SysUser();
        vo.setId(userId);
        vo.setLoginpwd(EncodeUtil.hash(password + salt, Constants.HASH_ENCODE) + Constants.PASSWORD_SALT_SPLIT + salt);
        sysUserMapper.update(vo);

        // Send password to user
        SysUser sysUser = sysUserMapper.findById(userId);
        smsService.sendSms(sysUser.getPhone(), SmsConstants.RESET_PASSWORD,
                new String[]{sysUser.getPhone(), sysUser.getLoginname(), password});
        sysUser.setLoginpwd(password);
        return new Result<>(sysUser, "注册成功！", 0, true);
    }
}
