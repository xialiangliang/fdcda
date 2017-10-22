package com.keyou.fdcda.home.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.keyou.fdcda.api.model.SysOutlets;
import com.keyou.fdcda.api.service.SysOutletsService;
import com.keyou.fdcda.api.utils.Assert;
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
import com.keyou.fdcda.api.model.SysDevice;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SysDeviceService;
import com.keyou.fdcda.home.controller.base.BaseController;

@Controller
@RequestMapping("/sysDevice")
public class SysDeviceController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(SysDeviceController.class);
	
	@Autowired
	private SysDeviceService sysDeviceService;
	@Autowired
	private SysOutletsService sysOutletsService;
	
	@RequestMapping(value="/new")
	public String add() throws Exception {		
		return "/page/sysDevice/new";
	}
	
	@RequestMapping(value="/find")	
	public String find(Long id, Model model, HttpServletRequest request){
		try {
			SysDevice sysDevice = sysDeviceService.findById(id);
			SysOutlets sysOutlets = sysOutletsService.findById(sysDevice.getOutletsId());
			Assert.isTrue(!getUser(request).getId().equals(sysOutlets.getUserId()), "违规操作");
			model.addAttribute("param", sysDevice);
			model.addAttribute(Constants.SUCCESS, true);
			return "/page/sysDevice/update";
		} catch (Exception e) {
			commonError(logger, e, "修改跳转异常", model);
			return "/page/sysDevice";
		}
	}
	
	@RequestMapping(value="/save")
	@ResponseBody
	public Map<String, Object> save(@ModelAttribute("sysDevice") SysDevice sysDevice,Model model) throws Exception {		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			sysDevice.setCreateTime(new Date());
			sysDeviceService.save(sysDevice);
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
	public Map<String, Object> update(@ModelAttribute("sysDevice") SysDevice sysDevice,Model model,HttpServletRequest request) throws Exception {		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			sysDevice.setModifyTime(new Date());
			sysDeviceService.update(sysDevice);
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
	public Map<String, Object> delete(Long id,Model model,HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SysDevice sysDevice = sysDeviceService.findById(id);
			sysDeviceService.deleteById(id);
			map.put(Constants.SUCCESS, true);
            map.put(Constants.MESSAGE, "删除成功");
		} catch (Exception e) {
			commonError(logger, e,"删除异常",map); 
		}
		return map;
	}
	
	@RequestMapping
	public String list(PaginationQuery query,Model model) throws Exception {		
		PageResult<SysDevice> pageList = sysDeviceService.findPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		return "/page/sysDevice/list";
	}
	
}


