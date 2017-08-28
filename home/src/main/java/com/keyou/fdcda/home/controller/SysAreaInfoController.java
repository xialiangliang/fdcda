package com.keyou.fdcda.home.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
import com.keyou.fdcda.api.model.SysAreaInfo;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SysAreaInfoService;
import com.keyou.fdcda.home.controller.base.BaseController;

@Controller
@RequestMapping("/sysAreaInfo")
public class SysAreaInfoController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(SysAreaInfoController.class);
	
	@Autowired
	private SysAreaInfoService sysAreaInfoService;
	
	@RequestMapping(value="/new")
	public String add() throws Exception {		
		return "/page/sysAreaInfo/new";
	}
	
	@RequestMapping(value="/find")	
	public String find(Long id, Model model){
		try {	
			SysAreaInfo sysAreaInfo = sysAreaInfoService.findById(id);
			model.addAttribute("param", sysAreaInfo);
			model.addAttribute(Constants.SUCCESS, true);
			return "/page/sysAreaInfo/update";
		} catch (Exception e) {
			commonError(logger, e, "修改跳转异常", model);
			return "/page/sysAreaInfo/update";
		}
	}
	
	@RequestMapping(value="/save")
	@ResponseBody
	public Map<String, Object> save(@ModelAttribute("sysAreaInfo") SysAreaInfo sysAreaInfo,Model model) {		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			sysAreaInfoService.save(sysAreaInfo);
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
	public Map<String, Object> update(@ModelAttribute("sysAreaInfo") SysAreaInfo sysAreaInfo,Model model) throws Exception {		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			sysAreaInfoService.update(sysAreaInfo);
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
			sysAreaInfoService.deleteById(id);
			map.put(Constants.SUCCESS, true);
            map.put(Constants.MESSAGE, "删除成功");
		} catch (Exception e) {
			commonError(logger, e,"删除异常",map); 
		}
		return map;
	}
	
	@RequestMapping
	public String list(PaginationQuery query,Model model) throws Exception{		
		PageResult<SysAreaInfo> pageList = sysAreaInfoService.findPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		return "/page/sysAreaInfo/list";
	}
	
}


