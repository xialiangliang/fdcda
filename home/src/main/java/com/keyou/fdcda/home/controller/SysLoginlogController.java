package com.keyou.fdcda.home.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
		
import com.keyou.fdcda.api.constants.Constants;
import com.keyou.fdcda.api.model.SysLoginlog;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SysLoginlogService;
import com.keyou.fdcda.home.controller.base.BaseController;

@Controller
@RequestMapping("/sysLoginlog")
public class SysLoginlogController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(SysLoginlogController.class);
	
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
	public Map<String, Object> save(@ModelAttribute("sysLoginlog") SysLoginlog sysLoginlog,Model model) throws Exception {		
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
	public String list(PaginationQuery query,Model model) throws Exception {		
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
	
}


