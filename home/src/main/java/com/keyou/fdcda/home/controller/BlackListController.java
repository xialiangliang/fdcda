package com.keyou.fdcda.home.controller;

import com.keyou.fdcda.api.constants.AreaConstants;
import com.keyou.fdcda.api.constants.Constants;
import com.keyou.fdcda.api.model.CustomerInfo;
import com.keyou.fdcda.api.model.SysBlacklistApply;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.CustomerInfoService;
import com.keyou.fdcda.api.service.SysBlacklistApplyService;
import com.keyou.fdcda.api.utils.Assert;
import com.keyou.fdcda.api.utils.StringUtil;
import com.keyou.fdcda.home.controller.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/blackList")
public class BlackListController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(BlackListController.class);
	
	@Autowired
	private CustomerInfoService customerInfoService;
	@Autowired
	private SysBlacklistApplyService sysBlacklistApplyService;

	@RequestMapping
	public String list() throws Exception {
		return "redirect:/blackList/user";
	}
	
	@RequestMapping("/user")
	public String userList(PaginationQuery query,Model model, HttpServletRequest request, String nameStr, String phoneStr) throws Exception {
		query.addQueryData("userRowId", getUser(request).getId().toString());
		query.addQueryData("isBlack", "1");
		query.addQueryData("nameStr", nameStr);
		query.addQueryData("phoneStr", phoneStr);
		PageResult<CustomerInfo> pageList = customerInfoService.findPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		model.addAttribute("countryMap", AreaConstants.countryMap);
		model.addAttribute("provinceMap", AreaConstants.provinceMap);
		model.addAttribute("cityMap", AreaConstants.cityMap);
		return "/page/blackList/user/list";
	}

	@RequestMapping(value="/user/find")
	public String userFind(Long id, Model model, HttpServletRequest request){
		try {
			CustomerInfo customerInfo = customerInfoService.findById(id);
			Assert.isTrue(customerInfo.getUserRowId() != null
					&& !getUser(request).getId().equals(customerInfo.getUserRowId()), "非法操作");
			Assert.isTrue(!customerInfo.getIsBlack().equals(1), "不在用户黑名单中");
			if (StringUtil.isNotBlank(customerInfo.getImageUrl())) {
				customerInfo.setImageUrl(customerInfo.getImageUrl().replaceAll("/mnt/facepics", "http://60.191.246.29:8880"));
			}
			model.addAttribute("param", customerInfo);
			model.addAttribute(Constants.SUCCESS, true);
			return "/page/blackList/user/detail";
		} catch (Exception e) {
			commonError(logger, e, "异常", model);
			return "redirect:/blackList/user";
		}
	}

	@RequestMapping("/user/listJson")
	@ResponseBody
	public Map<String, Object> userListJson(PaginationQuery query,Model model, HttpServletRequest request, String nameStr, String phoneStr, Integer page, Integer limit) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		this.formatPageQuery(query, page, limit);
		query.addQueryData("userRowId", getUser(request).getId().toString());
		query.addQueryData("isBlack", "1");
		query.addQueryData("nameStr", nameStr);
		query.addQueryData("phoneStr", phoneStr);
		if (StringUtil.isNotBlank(phoneStr) && phoneStr.length() == 13) {
			query.addQueryData("phone", phoneStr);
		}
		PageResult<CustomerInfo> pageList = customerInfoService.findPage(query);
		map.put("data", pageList.getRows());
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", pageList.getRowCount());
		map.put("query", query.getQueryData());
		map.put("countryMap", AreaConstants.countryMap);
		map.put("provinceMap", AreaConstants.provinceMap);
		map.put("cityMap", AreaConstants.cityMap);
		return map;
	}

	@RequestMapping(value="/user/remove")
	@ResponseBody
	public Map<String, Object> remove(Long id, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CustomerInfo customerInfo = customerInfoService.findById(id);
			Assert.isTrue(customerInfo.getUserRowId() != null
					&& !getUser(request).getId().equals(customerInfo.getUserRowId()), "非法操作");
			customerInfo.setIsBlack(0);
			customerInfo.setModifyTime(new Date());
			customerInfoService.update(customerInfo);
			map.put(Constants.SUCCESS, true);
			map.put(Constants.MESSAGE, "移除黑名单成功");
		} catch (Exception e) {
			commonError(logger, e,"移除黑名单异常",map);
		}
		return map;
	}
	
	@RequestMapping("/system")
	public String systemList(PaginationQuery query,Model model, HttpServletRequest request, String nameStr, String phoneStr) throws Exception {
		query.addQueryData("nameStr", nameStr);
		query.addQueryData("phoneStr", phoneStr);
		query.addQueryData("isBlack", "2");
		PageResult<CustomerInfo> pageList = customerInfoService.findSystemBlackPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		model.addAttribute("countryMap", AreaConstants.countryMap);
		model.addAttribute("provinceMap", AreaConstants.provinceMap);
		model.addAttribute("cityMap", AreaConstants.cityMap);
		return "/page/blackList/system/list";
	}

	@RequestMapping(value="/system/find")
	public String systemFind(Long id, Model model, HttpServletRequest request){
		try {
			CustomerInfo customerInfo = customerInfoService.findById(id);
			Assert.isTrue(!customerInfo.getIsBlack().equals(2), "用户不在系统黑名单中");
			if (StringUtil.isNotBlank(customerInfo.getImageUrl())) {
				customerInfo.setImageUrl(customerInfo.getImageUrl().replaceAll("/mnt/facepics", "http://60.191.246.29:8880"));
			}
//			Assert.isTrue(customerInfo.getUserRowId() != null
//					&& !getUser(request).getId().equals(customerInfo.getUserRowId()), "非法操作");
			model.addAttribute("param", customerInfo);
			model.addAttribute(Constants.SUCCESS, true);
			return "/page/blackList/system/detail";
		} catch (Exception e) {
			commonError(logger, e, "异常", model);
			return "redirect:/blackList/system";
		}
	}

	@RequestMapping("/system/listJson")
	@ResponseBody
	public Map<String, Object> systemListJson(PaginationQuery query,Model model, HttpServletRequest request, String nameStr, String phoneStr, Integer page, Integer limit) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		this.formatPageQuery(query, page, limit);
		query.addQueryData("isBlack", "2");
		query.addQueryData("nameStr", nameStr);
		query.addQueryData("phoneStr", phoneStr);
		if (StringUtil.isNotBlank(phoneStr) && phoneStr.length() == 13) {
			query.addQueryData("phone", phoneStr);
		}
		PageResult<CustomerInfo> pageList = customerInfoService.findPage(query);
		map.put("data", pageList.getRows());
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", pageList.getRowCount());
		map.put("query", query.getQueryData());
		map.put("countryMap", AreaConstants.countryMap);
		map.put("provinceMap", AreaConstants.provinceMap);
		map.put("cityMap", AreaConstants.cityMap);
		return map;
	}

	@RequestMapping(value="/user/applySystemBlacklist")
	@ResponseBody
	public Map<String, Object> applySystemBlacklist(Long id, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CustomerInfo customerInfo = customerInfoService.findById(id);
			Assert.isTrue(customerInfo.getUserRowId() != null
					&& !getUser(request).getId().equals(customerInfo.getUserRowId()), "非法操作");
			SysBlacklistApply sysBlacklistApply = new SysBlacklistApply();
			sysBlacklistApply.setCreateTime(new Date());
			sysBlacklistApply.setCustomerId(customerInfo.getId());
			sysBlacklistApply.setState(0);
			sysBlacklistApply.setUserId(getUser(request).getId());
			sysBlacklistApplyService.save(sysBlacklistApply);
			map.put(Constants.SUCCESS, true);
			map.put(Constants.MESSAGE, "等待审核");
		} catch (Exception e) {
			commonError(logger, e,"申请系统黑名单异常",map);
		}
		return map;
	}
	
}


