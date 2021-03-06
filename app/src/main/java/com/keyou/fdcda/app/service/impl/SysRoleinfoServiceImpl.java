package com.keyou.fdcda.app.service.impl;

import com.keyou.fdcda.api.constants.RedisConstants;
import com.keyou.fdcda.api.model.SysRoleinfo;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.RedisService;
import com.keyou.fdcda.api.service.SysRoleinfoService;
import com.keyou.fdcda.app.dao.SysRoleinfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("sysRoleinfoService")
public class SysRoleinfoServiceImpl implements SysRoleinfoService {
    private final static Logger logger = LoggerFactory.getLogger(SysRoleinfoServiceImpl.class);

    @Autowired
    private SysRoleinfoMapper sysRoleinfoMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public SysRoleinfo findById(Long id) {
        return sysRoleinfoMapper.findById(id);
    }

    @Override
    public Long update(SysRoleinfo entity) {
        return sysRoleinfoMapper.update(entity);
    }

    @Override
    public void deleteById(Long id) {
        sysRoleinfoMapper.deleteById(id);
    }

    @Override
    public Integer save(SysRoleinfo entity) {
        return sysRoleinfoMapper.save(entity);
    }

    @Override
    public Long findPageCount(Map<String, Object> map) {
        return sysRoleinfoMapper.findPageCount(map);
    }

    @Override
    public PageResult<SysRoleinfo> findPage(PaginationQuery query) {
        List<SysRoleinfo> list = new ArrayList<>();
        Long count = sysRoleinfoMapper.findPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = sysRoleinfoMapper.findPage(query.getQueryData());
        }
        return new PageResult<>(list, count.intValue(), query);
    }

    @Override
    public List<SysRoleinfo> findAllPage(Map<String, Object> map) {
        return sysRoleinfoMapper.findAllPage(map);
    }
    
    @Override
    public void updateByMap(Long roleId, Map<String, Object> updatedDataMap) {
        updatedDataMap.forEach((key, value)->{
            Long resoureId = Long.valueOf(key);
            if (resoureId != null) {
                Map<String, Object> query = new HashMap<>();
                query.put("roleId", roleId.toString());
                query.put("resourceId", resoureId.toString());
                List<SysRoleinfo> sysRoleinfoList = sysRoleinfoMapper.findAllPage(query);
                if (sysRoleinfoList.isEmpty() && "1".equals(value)) {
                    SysRoleinfo sysRoleinfo = new SysRoleinfo();
                    sysRoleinfo.setCreateTime(new Date());
                    sysRoleinfo.setResourceId(resoureId);
                    sysRoleinfo.setRoleId(roleId);
                    sysRoleinfoMapper.save(sysRoleinfo);
                } else if (!sysRoleinfoList.isEmpty() && "0".equals(value)) {
                    sysRoleinfoList.forEach(item->{
                        sysRoleinfoMapper.deleteById(item.getId());
                    });
                }
                redisService.del(RedisConstants.RESOURCE_ROLE + resoureId.toString());
            }
        });
    }
}
