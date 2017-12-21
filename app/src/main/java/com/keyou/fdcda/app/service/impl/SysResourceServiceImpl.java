package com.keyou.fdcda.app.service.impl;

import com.google.common.collect.Maps;
import com.keyou.fdcda.api.constants.RedisConstants;
import com.keyou.fdcda.api.model.SysResource;
import com.keyou.fdcda.api.model.SysRoleinfo;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.RedisService;
import com.keyou.fdcda.api.service.SysResourceService;
import com.keyou.fdcda.api.utils.Result;
import com.keyou.fdcda.app.dao.SysResourceMapper;
import com.keyou.fdcda.app.dao.SysRoleinfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service("sysResourceService")
public class SysResourceServiceImpl implements SysResourceService {
    private final static Logger logger = LoggerFactory.getLogger(SysResourceServiceImpl.class);

    @Autowired
    private SysResourceMapper sysResourceMapper;
    @Autowired
    private SysRoleinfoMapper sysRoleinfoMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public SysResource findById(Long id) {
        return sysResourceMapper.findById(id);
    }

    @Override
    public Long update(SysResource entity) {
        return sysResourceMapper.update(entity);
    }

    @Override
    public void deleteById(Long id) {
        sysResourceMapper.deleteById(id);
    }

    @Override
    public Integer save(SysResource entity) {
        return sysResourceMapper.save(entity);
    }

    @Override
    public Long findPageCount(Map<String, Object> map) {
        return sysResourceMapper.findPageCount(map);
    }

    @Override
    public PageResult<SysResource> findPage(PaginationQuery query) {
        List<SysResource> list = new ArrayList<>();
        Long count = sysResourceMapper.findPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = sysResourceMapper.findPage(query.getQueryData());
        }
        return new PageResult<>(list, count.intValue(), query);
    }

    @Override
    public List<SysResource> findAllPage(Map<String, Object> map) {
        return sysResourceMapper.findAllPage(map);
    }

    @Override
    public List<SysResource> getTopResource(Long userId) {
        List<Long> idList = sysResourceMapper.findTopResourceId(userId);
        List<SysResource> topSourceList = new ArrayList<>();
        if (idList.contains(0L)) { // all resource
            Map<String, Object> query = new HashMap<>();
            query.put("parentId", "0");
            topSourceList = sysResourceMapper.findAllPage(query);
            topSourceList.forEach(sysResource -> {
                Map<String, Object> query1 = new HashMap<>();
                query1.put("parentId", sysResource.getId().toString());
                List<SysResource> subResource = sysResourceMapper.findAllPage(query1);
                if (subResource.size() > 0) {
                    sysResource.setSubResource(subResource);
                }
            });
        } else {
            if (!idList.isEmpty()) {
                topSourceList = sysResourceMapper.findByIds(idList);
                topSourceList = topSourceList.stream().filter(item-> item.getParentId().equals(0L)).collect(Collectors.toList());
                topSourceList.forEach(sysResource -> {
                    Map<String, Object> query1 = new HashMap<>();
                    query1.put("parentId", sysResource.getId().toString());
                    query1.put("userId", userId.toString());
                    List<SysResource> subResource = sysResourceMapper.findSubResource(query1);
                    if (subResource.size() > 0) {
                        sysResource.setSubResource(subResource);
                    }
                });
            }
        }
        return topSourceList;
    }

    @Override
    public List<SysResource> getTopResourceList(Long userId) {
        List<Long> idList = sysResourceMapper.findTopResourceId(userId);
        if (idList.isEmpty()) {
            return new ArrayList<>();
        }
        // 查询一级菜单
        String idStrs = null;
        List<Long> ids = null;
        if (!idList.contains(0L)) {
            idStrs = idList.stream().map(Object::toString).collect(Collectors.toList()).stream().reduce((a, b) -> a + "," + b).get();
            ids = idList;
        }
        String finalIdStrs = idStrs;
        Map<String, Object> query1 = new HashMap<>();
        query1.put("parentId", "0");
        query1.put("ids", ids);
        query1.put("type", "1");
        List<SysResource> topResourceList = sysResourceMapper.findAllPage(query1);

        return topResourceList;
    }

    @Override
    public SysResource getTopResource(Long userId, Long topResourceId) {
        List<Long> idList = sysResourceMapper.findTopResourceId(userId);
        if (idList.isEmpty() || (!idList.contains(0L) && !idList.contains(topResourceId))) {
            return null;
        }
        // 查询顶级资源
        SysResource topResource = sysResourceMapper.findById(topResourceId);
        // 查询一级菜单
        String idStrs = null;
        List<Long> ids = null;
        if (!idList.contains(0L)) {
            idStrs = idList.stream().map(Object::toString).collect(Collectors.toList()).stream().reduce((a, b) -> a + "," + b).get();
            ids = idList;
        }
        String finalIdStrs = idStrs;
        List<Long> finalIds = ids;
        Map<String, Object> query1 = new HashMap<>();
        query1.put("parentId", topResource.getId().toString());
        query1.put("ids", ids);
        query1.put("type", "1");
        List<SysResource> subResource = sysResourceMapper.findAllPage(query1);
        if (subResource.size() > 0) {
            topResource.setSubResource(subResource);
            // 查询二级菜单
            subResource.forEach(sysResource -> {
                Map<String, Object> query2 = new HashMap<>();
                query2.put("parentId", sysResource.getId().toString());
                query2.put("ids", finalIds);
                query2.put("type", "1");
                List<SysResource> subResource2 = sysResourceMapper.findAllPage(query2);
                if (subResource2.size() > 0) {
                    sysResource.setSubResource(subResource2);
                }
            });
        }
        
        return topResource;
    }

    @Override
    public Result<List<SysResource>> getTopologicalResource(Map<String, Object> map) {
        List<SysResource> topResourceList = new ArrayList<>();
        map.put("sortByParent", "asc");
        List<SysResource> resourceList;
        if (map.get("roleId") != null) {
            resourceList = sysResourceMapper.findAllPageWithAuth(map);
        } else {
            resourceList = sysResourceMapper.findAllPage(map);
        }
        Map<Long, SysResource> resMap = new HashMap<>();
        resourceList.forEach(resource -> resMap.put(resource.getId(), resource));
        for (SysResource sysResource : resourceList) {
            if (sysResource.getParentId().equals(0L)) {
                topResourceList.add(sysResource);
            } else {
                SysResource res = resMap.get(sysResource.getParentId());
                if (res != null) {
                    if (res.getSubResource() == null) {
                        res.setSubResource(new ArrayList<>());
                    }
                    res.getSubResource().add(sysResource);
                }
            }
        }
        return new Result<>(topResourceList, "", 0, true);
    }

    @Override
    public List<SysResource> findByUrl(String url) {
        List<SysResource> list = sysResourceMapper.findByUrl(url);
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        return list.stream().filter(sysResource -> {
            List<String> urlList = Arrays.asList(sysResource.getUrl().split(";"));
            return urlList.contains(url);
        }).collect(Collectors.toList());
    }

    @Override
    public List<SysResource> findListWithRole() {
        Map<String, Object> map = new HashMap<>();
        List<SysResource> list = sysResourceMapper.findListWithRole(map);
        map = new HashMap<>();
        map.put("resourceId", "0");
        List<SysRoleinfo> superRoleinfoList = sysRoleinfoMapper.findAllPage(map);

        list.forEach(sysResource -> {
            String roleIdStr = sysResource.getRoleIdsStr();
            for (SysRoleinfo sysRoleinfo : superRoleinfoList) {
                roleIdStr = roleIdStr == null ? "" : roleIdStr;
                if (roleIdStr.equals("")) {
                    roleIdStr = sysRoleinfo.getRoleId().toString();
                } else {
                    roleIdStr = roleIdStr + "," + sysRoleinfo.getRoleId().toString();
                }
            }
            sysResource.setRoleIdsStr(roleIdStr);
            redisService.set(RedisConstants.RESOURCE_ROLE + sysResource.getId(), roleIdStr);
        });
        return list;
    }

    @Override
    public List<Map> getTopologicalResourceJsonData(Map<String, Object> map) {
        map.put("sortByParent", "asc");
        List<SysResource> resourceList;
        if (map.get("roleId") != null) {
            resourceList = sysResourceMapper.findAllPageWithAuth(map);
        } else {
            resourceList = sysResourceMapper.findAllPage(map);
        }
        List<Map> list = new ArrayList<>();
        resourceList.forEach(resource->{
            Map<String, Object> m = Maps.newHashMap();
            m.put("id", resource.getId());
            m.put("pId", resource.getParentId());
            m.put("name", resource.getName());
            m.put("open", false);
            m.put("dir", resource.getUrl());
            m.put("sort", resource.getSort());
            m.put("checked", resource.getAuth() != null && resource.getAuth().equals(1L));
            list.add(m);
        });
        return list;
    }
}
