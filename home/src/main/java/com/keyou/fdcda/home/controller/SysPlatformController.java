package com.keyou.fdcda.home.controller;

import com.keyou.fdcda.api.constants.Constants;
import com.keyou.fdcda.api.model.SysLoginlog;
import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SysLoginlogService;
import com.keyou.fdcda.api.service.SysUserService;
import com.keyou.fdcda.home.controller.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzq on 2017-08-27.
 */
@Controller
@RequestMapping("/sysPlatform")
public class SysPlatformController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(SysPlatformController.class);

    @Autowired
    private SysLoginlogService sysLoginlogService;
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping(value="/new")
    public String add() throws Exception {
        return "/page/sysLoginlog/new";
    }

    @RequestMapping(value="/find")
    public String find(Long id, Model model){
        try {
            SysLoginlog sysLoginlog = sysLoginlogService.findById(id);
            model.addAttribute("param", sysLoginlog);
            model.addAttribute(Constants.SUCCESS, true);
            return "/page/sysLoginlog/update";
        } catch (Exception e) {
            commonError(logger, e, "修改跳转异常", model);
            return "/page/sysLoginlog";
        }
    }

    @RequestMapping(value="/save")
    @ResponseBody
    public Map<String, Object> save(@ModelAttribute("sysLoginlog") SysLoginlog sysLoginlog, Model model) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            sysLoginlogService.save(sysLoginlog);
            model.addAttribute(Constants.SUCCESS, true);
            map.put(Constants.SUCCESS, true);
            map.put(Constants.MESSAGE, "添加成功");

        } catch (Exception e) {
            commonError(logger, e, "添加异常",map);
        }
        return map;
    }

    @RequestMapping(value="/update")
    @ResponseBody
    public Map<String, Object> update(@ModelAttribute("sysLoginlog") SysLoginlog sysLoginlog,Model model) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            sysLoginlogService.update(sysLoginlog);
            model.addAttribute(Constants.SUCCESS, true);
            map.put(Constants.SUCCESS, true);
            map.put(Constants.MESSAGE, "修改成功");
        } catch (Exception e) {
            commonError(logger, e,"修改异常",map);
        }
        return map;
    }

    @RequestMapping(value="/delete")
    @ResponseBody
    public Map<String, Object> delete(Long id,Model model) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            sysLoginlogService.deleteById(id);
            map.put(Constants.SUCCESS, true);
            map.put(Constants.MESSAGE, "删除成功");
        } catch (Exception e) {
            commonError(logger, e,"删除异常",map);
        }
        return map;
    }

    @RequestMapping
    public String list(PaginationQuery query,Model model,Long userId) throws Exception {
        if (userId != null) {
            query.addQueryData("userId", userId.toString());
        }
        PageResult<SysLoginlog> pageList = sysLoginlogService.findPage(query);
        if (pageList.getRowCount() > 0) {
            pageList.getRows().forEach(sysLoginlog -> {
                SysUser user = sysUserService.findById(sysLoginlog.getUserId());
                sysLoginlog.setName(user.getUsername());
                sysLoginlog.setLoginName(user.getLoginname());
            });
        }
        model.addAttribute("result", pageList);
        model.addAttribute("query", query.getQueryData());
        return "/page/sysLoginlog/list";
    }

    @RequestMapping("/listJson")
    @ResponseBody
    public Map<String, Object> listJson(HttpServletRequest request, PaginationQuery query, Long userId, Integer page, Integer limit) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        this.formatPageQuery(query, page, limit);
        if (userId != null) {
            query.addQueryData("userId", userId.toString());
        }
        PageResult<SysLoginlog> pageList = sysLoginlogService.findPage(query);
        if (pageList.getRowCount() > 0) {
            pageList.getRows().forEach(sysLoginlog -> {
                SysUser user = sysUserService.findById(sysLoginlog.getUserId());
                sysLoginlog.setName(user.getUsername());
                sysLoginlog.setLoginName(user.getLoginname());
            });
        }
        map.put("data", pageList.getRows());
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", pageList.getRowCount());
        map.put("query", query.getQueryData());
        return map;
    }
}
