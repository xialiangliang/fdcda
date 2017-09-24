package com.keyou.fdcda.home.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.keyou.fdcda.api.model.SysGoodCategory;
import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.service.SysGoodCategoryService;
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
import com.keyou.fdcda.api.model.SysGood;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SysGoodService;
import com.keyou.fdcda.home.controller.base.BaseController;

@Controller
@RequestMapping("/sysGood")
public class SysGoodController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(SysGoodController.class);
	
	@Autowired
	private SysGoodService sysGoodService;
	@Autowired
	private SysGoodCategoryService sysGoodCategoryService;
	
	@RequestMapping(value="/new")
	public String add() throws Exception {		
		return "/page/sysGood/new";
	}
	
	@RequestMapping(value="/find")	
	public String find(Long id, Model model){
		try {	
			SysGood sysGood = sysGoodService.findById(id);
			model.addAttribute("param", sysGood);
			model.addAttribute(Constants.SUCCESS, true);
			return "/page/sysGood/update";
		} catch (Exception e) {
			commonError(logger, e, "修改跳转异常", model);
			return "/page/sysGood";
		}
	}
	
	@RequestMapping(value="/save")
	@ResponseBody
	public Map<String, Object> save(@ModelAttribute("sysGood") SysGood sysGood,Model model) throws Exception {		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			sysGoodService.save(sysGood);
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
	public Map<String, Object> update(@ModelAttribute("sysGood") SysGood sysGood,Model model) throws Exception {		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			sysGoodService.update(sysGood);
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
			sysGoodService.deleteById(id);
			map.put(Constants.SUCCESS, true);
            map.put(Constants.MESSAGE, "删除成功");
		} catch (Exception e) {
			commonError(logger, e,"删除异常",map); 
		}
		return map;
	}
	
	@RequestMapping
	public String list(PaginationQuery query, Model model, HttpServletRequest request) throws Exception {
		Long userId = ((SysUser)request.getSession().getAttribute(Constants.SESSION_USER)).getId();
		query.addQueryData("userId", userId.toString());
		PageResult<SysGood> pageList = sysGoodService.findPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		return "/page/sysGood/list";
	}


	@RequestMapping("/category")
	public String category(PaginationQuery query,Model model) throws Exception {
		PageResult<SysGoodCategory> pageList = sysGoodCategoryService.findPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		return "/page/sysGood/category";
	}
	
	@RequestMapping("/category/listMap")
	@ResponseBody
	public Map<String, Object> categoryMap(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> query = new HashMap<>();
		query.put("sortByParent", "asc");
		Result<List<SysGoodCategory>> result = sysGoodCategoryService.getTopologicalCategory(query);
		map.put("result", result.getData());
		return map;
	}

	@RequestMapping(value="/category/new")
	public String categoryAdd(Long parentId, Model model) throws Exception {
		model.addAttribute("parentId", parentId);
		return "/page/sysGood/category-new";
	}

	@RequestMapping(value="/category/find")
	public String categoryFind(Long id, Model model){
		try {
			SysGoodCategory sysGoodCategory = sysGoodCategoryService.findById(id);
			model.addAttribute("param", sysGoodCategory);
			model.addAttribute(Constants.SUCCESS, true);
			return "/page/sysGood/category-update";
		} catch (Exception e) {
			commonError(logger, e, "修改跳转异常", model);
			return "/page/sysGood";
		}
	}

	@RequestMapping(value="/category/save")
	@ResponseBody
	public Map<String, Object> categorySave(@ModelAttribute("sysGoodCategory") SysGoodCategory sysGoodCategory,Model model) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			sysGoodCategory.setId(null);
			if (sysGoodCategory.getParentId() == null) {
				sysGoodCategory.setParentId(0L);
			}
			sysGoodCategory.setCreateTime(new Date());
			sysGoodCategoryService.save(sysGoodCategory);
			model.addAttribute(Constants.SUCCESS, true);
			map.put(Constants.SUCCESS, true);
			map.put(Constants.MESSAGE, "添加成功");

		} catch (Exception e) {
			commonError(logger, e, "添加异常",map);
		}
		return map;
	}

	@RequestMapping(value="/category/update")
	@ResponseBody
	public Map<String, Object> categoryUpdate(@ModelAttribute("sysGoodCategory") SysGoodCategory sysGoodCategory,Model model) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			sysGoodCategoryService.update(sysGoodCategory);
			model.addAttribute(Constants.SUCCESS, true);
			map.put(Constants.SUCCESS, true);
			map.put(Constants.MESSAGE, "修改成功");
		} catch (Exception e) {
			commonError(logger, e,"修改异常",map);
		}
		return map;
	}
}


