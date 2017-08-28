package com.keyou.fdcda.app.service.impl;

import com.google.common.collect.Maps;
import com.keyou.fdcda.api.model.SysResource;
import com.keyou.fdcda.api.model.SysRole;
import com.keyou.fdcda.api.model.SysTest;
import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SysManagerService;
import com.keyou.fdcda.app.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Wataru on 2017-07-08.
 */
@Service("sysManagerService")
public class SysManagerServiceImpl implements SysManagerService {
    private static final Logger logger = LoggerFactory.getLogger(SysManagerServiceImpl.class);
    
    @Autowired
    private SysResourceMapper sysResourceMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleinfoMapper sysRoleinfoMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysTestMapper sysTestMapper;
    
    @Override
    public List<SysResource> getTopResource(Long userId) {
        List<Long> idList = sysResourceMapper.findTopResourceId(userId);
        List<SysResource> topSourceList;
        if (idList.contains(0L)) { // all resource
            Map<String, Object> query = new HashMap<>();
            query.put("parentId", "0");
            topSourceList = sysResourceMapper.findAllPage(query);
            topSourceList.forEach(sysResource -> {
                Map<String, Object> query1 = new HashMap<>();
                query1.put("parentId", sysResource.getId().toString());
                List<SysResource> subResource = sysResourceMapper.findAllPage(query1);
                sysResource.setSubResource(subResource);
            });
        } else {
            topSourceList = sysResourceMapper.findByIds(idList);
            topSourceList.forEach(sysResource -> {
                Map<String, Object> query1 = new HashMap<>();
                query1.put("parentId", sysResource.getId().toString());
                List<SysResource> subResource = sysResourceMapper.findSubResource(query1);
                sysResource.setSubResource(subResource);
            });
        }
        return topSourceList;
    }
    
    @Override
    public SysUser getUserByPhone(String phone) {
        return sysUserMapper.getUserByPhone(phone);
    }

    @Override
    public PageResult<SysUser> findUserPage(PaginationQuery query) {
        List<SysUser> list = new ArrayList<>();
        Long count = sysUserMapper.findPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = sysUserMapper.findPage(query.getQueryData());
        }
        return new PageResult<>(list, query, count);
    }
    
    @Override
    public void test() {
        Map<String, Object> map = Maps.newHashMap();
        //map.put("user_id", "6");
        map.put("content", "11112221");
        map.put("startRecord", 5);
        map.put("endRecord", 3);
        SysTest test = new SysTest();
        test.setContent("2211");
        test.setCreateTime(new Date());
        test.setRoleId(110L);
        test.setUserId(120L);
        Object a;
        a = sysTestMapper.findById(23L);
        a = sysTestMapper.findPageCount(map);
        a = sysTestMapper.findPage(map);
        a = sysTestMapper.findAllPage(map);
        sysTestMapper.deleteById(25L);
        a = sysTestMapper.save(test);
        test = new SysTest();
        test.setId(53l);
        test.setRoleId(88l);
        a = sysTestMapper.update(test);
        test.setId(1113l);
        test.setRoleId(88l);
        a = sysTestMapper.update(test);
        a = 1;
    }
}
