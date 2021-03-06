package com.keyou.fdcda.home.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.keyou.fdcda.api.model.SysDevice;
import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.service.SysDeviceService;
import com.keyou.fdcda.api.service.SysUserService;
import com.keyou.fdcda.api.utils.Assert;
import com.keyou.fdcda.api.utils.StringUtil;
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
import com.keyou.fdcda.api.model.SysOutlets;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SysOutletsService;
import com.keyou.fdcda.home.controller.base.BaseController;

@Controller
@RequestMapping("/sysOutlets")
public class SysOutletsController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(SysOutletsController.class);
	
	@Autowired
	private SysOutletsService sysOutletsService;
	@Autowired
	private SysDeviceService sysDeviceService;
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping(value="/new")
	public String add() throws Exception {		
		return "/page/sysOutlets/new";
	}
	
	@RequestMapping(value="/find")	
	public String find(Long id, Model model, HttpServletRequest request){
		try {	
			SysOutlets sysOutlets = sysOutletsService.findById(id);
			SysUser user = sysUserService.findById(sysOutlets.getUserId());
			if (user != null) {
				sysOutlets.setLoginName(user.getLoginname());
			}
			model.addAttribute("param", sysOutlets);
			model.addAttribute(Constants.SUCCESS, true);
			return "/page/sysOutlets/update";
		} catch (Exception e) {
			commonError(logger, e, "修改跳转异常", model);
			return "/page/sysOutlets";
		}
	}
	
	@RequestMapping(value="/save")
	@ResponseBody
	public Map<String, Object> save(@ModelAttribute("sysOutlets") SysOutlets sysOutlets,Model model) throws Exception {		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Assert.isBlank(sysOutlets.getLoginName(), "用户登录名不能为空");
			SysUser user = sysUserService.getUserByLoginname(sysOutlets.getLoginName());
			Assert.isNull(user, "用户不存在");
			sysOutlets.setUserId(user.getId());
			sysOutlets.setCreateTime(new Date());
			sysOutletsService.save(sysOutlets);
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
	public Map<String, Object> update(@ModelAttribute("sysOutlets") SysOutlets sysOutlets,Model model,HttpServletRequest request) throws Exception {		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Assert.isBlank(sysOutlets.getLoginName(), "用户登录名不能为空");
			SysUser user = sysUserService.getUserByLoginname(sysOutlets.getLoginName());
			Assert.isNull(user, "用户不存在");
			sysOutlets.setUserId(user.getId());
			sysOutlets.setModifyTime(new Date());
			sysOutletsService.update(sysOutlets);
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
			sysOutletsService.deleteById(id);
			map.put(Constants.SUCCESS, true);
            map.put(Constants.MESSAGE, "删除成功");
		} catch (Exception e) {
			commonError(logger, e,"删除异常",map); 
		}
		return map;
	}
	
	@RequestMapping
	public String list(PaginationQuery query,Model model,HttpServletRequest request) throws Exception {		
		PageResult<SysOutlets> pageList = sysOutletsService.findPage(query);
//		query.addQueryData("userId", getUser(request).getId().toString());
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		return "/page/sysOutlets/list";
	}

	@RequestMapping("/listJson")
	@ResponseBody
	public Map<String, Object> listJson(PaginationQuery query,Model model, HttpServletRequest request, Integer page, Integer limit) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		this.formatPageQuery(query, page, limit);
//		query.addQueryData("userId", getUser(request).getId().toString());
		PageResult<SysOutlets> pageList = sysOutletsService.findPage(query);
		pageList.getRows().forEach(sysOutlets -> {
			SysUser user = sysUserService.findById(sysOutlets.getUserId());
			if (user != null) {
				sysOutlets.setLoginName(user.getLoginname());
			}
		});
		map.put("data", pageList.getRows());
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", pageList.getRowCount());
		map.put("query", query.getQueryData());
		return map;
	}

	@RequestMapping("/sysDevice/list")
	public String deviceList(PaginationQuery query,Model model,Long outletsId, HttpServletRequest request) throws Exception {
		query.addQueryData("outletsId", outletsId.toString());
//		PageResult<SysDevice> pageList = sysDeviceService.findPage(query);
//		model.addAttribute("result", pageList);
		model.addAttribute("outletsId", outletsId);
		model.addAttribute("query", query.getQueryData());
		return "/page/sysOutlets/sysDevice/list";
	}

	@RequestMapping("/sysDevice/listJson")
	@ResponseBody
	public Map<String, Object> listJson(PaginationQuery query,Model model, HttpServletRequest request, Long outletsId, Integer page, Integer limit) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		this.formatPageQuery(query, page, limit);
		query.addQueryData("outletsId", outletsId.toString());
//		query.addQueryData("userId", getUser(request).getId().toString());
		PageResult<SysDevice> pageList = sysDeviceService.findPage(query);
		pageList.getRows().forEach(sysDevice -> {
			SysOutlets sysOutlets = sysOutletsService.findById(sysDevice.getOutletsId());
			if (sysOutlets != null) {
				sysDevice.setOutletsName(sysOutlets.getName());
			}
		});
		map.put("data", pageList.getRows());
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", pageList.getRowCount());
		map.put("query", query.getQueryData());
		return map;
	}

	@RequestMapping(value="/sysDevice/new")
	public String sysDeviceAdd(Model model,Long outletsId) throws Exception {
		model.addAttribute("outletsId", outletsId);
		return "/page/sysOutlets/sysDevice/new";
	}

	@RequestMapping(value="/sysDevice/find")
	public String sysDeviceFind(Long id, Model model, HttpServletRequest request){
		try {
			SysDevice sysDevice = sysDeviceService.findById(id);
			model.addAttribute("param", sysDevice);
			model.addAttribute(Constants.SUCCESS, true);
			return "/page/sysOutlets/sysDevice/update";
		} catch (Exception e) {
			commonError(logger, e, "修改跳转异常", model);
			return "/page/sysOutlets/sysDevice";
		}
	}

	@RequestMapping(value="/sysDevice/save")
	@ResponseBody
	public Map<String, Object> sysDeviceSave(@ModelAttribute("sysDevice") SysDevice sysDevice,Model model) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Assert.isTrue(StringUtil.isBlank(sysDevice.getSeqno()), "设备序列号不能为空");
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

	@RequestMapping(value="/sysDevice/update")
	@ResponseBody
	public Map<String, Object> sysDeviceUpdate(@ModelAttribute("sysDevice") SysDevice sysDevice,Model model,HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Assert.isTrue(StringUtil.isBlank(sysDevice.getSeqno()), "设备序列号不能为空");
//			SysDevice sysDevice1 = sysDeviceService.findById(sysDevice.getId());
//			SysOutlets sysOutlets = sysOutletsService.findById(sysDevice1.getOutletsId());
//			Assert.isTrue(!getUser(request).getId().equals(sysOutlets.getUserId()), "违规操作");
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

	@RequestMapping(value="/sysDevice/delete")
	@ResponseBody
	public Map<String, Object> sysDeviceDelete(Long id,Model model,HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
//			SysDevice sysDevice = sysDeviceService.findById(id);
//			SysOutlets sysOutlets = sysOutletsService.findById(sysDevice.getOutletsId());
//			Assert.isTrue(!getUser(request).getId().equals(sysOutlets.getUserId()), "违规操作");
			sysDeviceService.deleteById(id);
			map.put(Constants.SUCCESS, true);
			map.put(Constants.MESSAGE, "删除成功");
		} catch (Exception e) {
			commonError(logger, e,"删除异常",map);
		}
		return map;
	}
	
}


