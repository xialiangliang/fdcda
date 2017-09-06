package com.keyou.fdcda.home.controller;

import java.util.*;

import javax.servlet.http.HttpServletResponse;

import com.keyou.fdcda.api.constants.IconConstants;
import com.keyou.fdcda.api.service.RedisService;
import com.keyou.fdcda.api.utils.Result;
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
import com.keyou.fdcda.api.model.SysResource;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SysResourceService;
import com.keyou.fdcda.home.controller.base.BaseController;

@Controller
@RequestMapping("/sysResource")
public class SysResourceController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(SysResourceController.class);
	
	@Autowired
	private SysResourceService sysResourceService;
	@Autowired
	private RedisService redisService;
	
	@RequestMapping(value="/new")
	public String add(Model model) throws Exception {
		model.addAttribute("iconList", IconConstants.ICON_LIST);
		return "/page/sysResource/new";
	}
	
	@RequestMapping(value="/find")	
	public String find(Long id, Model model){
		try {	
			SysResource sysResource = sysResourceService.findById(id);
			model.addAttribute("param", sysResource);
			Map<String, Object> query = new HashMap<>();
			query.put("parentId", "0");
			List<SysResource> parentList = sysResourceService.findAllPage(query);
			model.addAttribute("parentList", parentList);
			model.addAttribute("iconList", IconConstants.ICON_LIST);
			model.addAttribute(Constants.SUCCESS, true);
			return "/page/sysResource/update";
		} catch (Exception e) {
			commonError(logger, e, "修改跳转异常", model);
			return "/page/sysResource";
		}
	}
	
	@RequestMapping(value="/save")
	@ResponseBody
	public Map<String, Object> save(@ModelAttribute("sysResource") SysResource sysResource,Model model) throws Exception {		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			sysResourceService.save(sysResource);
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
	public Map<String, Object> update(@ModelAttribute("sysResource") SysResource sysResource,Model model) throws Exception {		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			sysResourceService.update(sysResource);
			redisService.del("resource_parent_id_" + sysResource.getUrl());
			redisService.del("resource_sub_id_" + sysResource.getUrl());
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
			sysResourceService.deleteById(id);
			map.put(Constants.SUCCESS, true);
            map.put(Constants.MESSAGE, "删除成功");
		} catch (Exception e) {
			commonError(logger, e,"删除异常",map); 
		}
		return map;
	}
	
	@RequestMapping
	public String list(PaginationQuery query,Model model) throws Exception {
		return "/page/sysResource/list";
	}

	@RequestMapping("/listMap")
	@ResponseBody
	public Map<String, Object> listMap() throws Exception {
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> query = new HashMap<>();
		query.put("sortByParent", "asc");
		Result<List<SysResource>> result = sysResourceService.getTopologicalResource(query);
		map.put("result", result.getData());
		return map;
	}
	
}


