package com.keyou.fdcda.home.controller;

import com.google.common.collect.Maps;
import com.keyou.fdcda.api.constants.AreaConstants;
import com.keyou.fdcda.api.constants.Constants;
import com.keyou.fdcda.api.model.BlacklistDetails;
import com.keyou.fdcda.api.model.CustomerInfo;
import com.keyou.fdcda.api.model.SysBlacklistApply;
import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.BlacklistDetailsService;
import com.keyou.fdcda.api.service.CustomerInfoService;
import com.keyou.fdcda.api.service.SysBlacklistApplyService;
import com.keyou.fdcda.api.utils.Assert;
import com.keyou.fdcda.api.utils.StringUtil;
import com.keyou.fdcda.api.utils.config.UrlConfig;
import com.keyou.fdcda.home.controller.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/blackList")
public class BlackListController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(BlackListController.class);
	
	@Autowired
	private CustomerInfoService customerInfoService;
	@Autowired
	private SysBlacklistApplyService sysBlacklistApplyService;
	@Autowired
	private BlacklistDetailsService blacklistDetailsService;
	@Autowired
	private UrlConfig urlConfig;

	@RequestMapping
	public String list() throws Exception {
		return "redirect:/blackList/user";
	}
	
	@RequestMapping("/user")
	public String userList(PaginationQuery query,Model model, HttpServletRequest request, String nameStr, String phoneStr, String cardStr) throws Exception {
		query.addQueryData("userRowId", getUser(request).getId().toString());
		query.addQueryData("isBlack", "1");
		query.addQueryData("nameStr", nameStr);
		query.addQueryData("phoneStr", phoneStr);
		query.addQueryData("cardStr", cardStr);
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
				customerInfo.setImageUrl(customerInfo.getImageUrl().replaceAll("/mnt/facepics", urlConfig.getImgPath()));
			}
			model.addAttribute("param", customerInfo);
			model.addAttribute(Constants.SUCCESS, true);
			return "/page/blackList/user/detail";
		} catch (Exception e) {
			commonError(logger, e, "异常", model);
			return "redirect:/blackList/user";
		}
	}

	@RequestMapping(value="/user/new")
	public String userNew(Model model, HttpServletRequest request){
		try {
			return "/page/blackList/user/new";
		} catch (Exception e) {
			commonError(logger, e, "异常", model);
			return "redirect:/blackList/user";
		}
	}

	@RequestMapping(value="/user/save")
	@ResponseBody
	public Map<String, Object> userSave(String name, String phone, Model model, HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Long id = getUser(request).getId();
			if (StringUtil.isBlank(name) && StringUtil.isBlank(phone)) {
				map.put(Constants.SUCCESS, false);
				map.put(Constants.MESSAGE, "输入用户名或手机号");
				return map;
			}
			List<CustomerInfo> ll1 = null;
			if (StringUtil.isNotBlank(name)) {
				Map<String, Object> query = Maps.newHashMap();
				query.put("name", name);
				query.put("userRowId", id.toString());
				ll1 = customerInfoService.findAllPage(query);
				if (ll1.isEmpty()) {
					map.put(Constants.SUCCESS, false);
					map.put(Constants.MESSAGE, "用户名不存在");
					return map;
				}
			}
			List<CustomerInfo> ll2 = null;
			if (StringUtil.isNotBlank(phone)) {
				Map<String, Object> query = Maps.newHashMap();
				query.put("phone", phone);
				query.put("userRowId", id.toString());
				ll2 = customerInfoService.findAllPage(query);
				if (ll2.isEmpty()) {
					map.put(Constants.SUCCESS, false);
					map.put(Constants.MESSAGE, "手机号不存在");
					return map;
				}
			}
			List<CustomerInfo> ll;
			if (ll1 != null) {
				ll = ll1;
				if (ll2 != null) {
					ll.retainAll(ll2);
				}
			} else {
				ll = ll2;
			}
			if (CollectionUtils.isEmpty(ll)) {
				map.put(Constants.SUCCESS, false);
				map.put(Constants.MESSAGE, "不存在满足条件的用户");
				return map;
			}
			if (ll.size() > 1) {
				map.put(Constants.SUCCESS, false);
				map.put(Constants.MESSAGE, "存在多个满足条件的用户");
				return map;
			}
			CustomerInfo customerInfo = ll.get(0);
			Assert.isTrue(customerInfo.getUserRowId() != null
					&& !getUser(request).getId().equals(customerInfo.getUserRowId()), "非法操作");
			customerInfo.setIsBlack(1);
			customerInfo.setIsVip(0); // 直接移出VIP
			customerInfo.setModifyTime(new Date());
			customerInfoService.update(customerInfo);
			map.put(Constants.SUCCESS, true);
			map.put(Constants.MESSAGE, "添加黑名单成功");
		} catch (Exception e) {
			commonError(logger, e,"添加黑名单异常",map);
		}
		return map;
	}

	@RequestMapping("/user/listJson")
	@ResponseBody
	public Map<String, Object> userListJson(PaginationQuery query,Model model, HttpServletRequest request, String nameStr, String phoneStr, String cardStr, Integer page, Integer limit) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		this.formatPageQuery(query, page, limit);
		query.addQueryData("userRowId", getUser(request).getId().toString());
		query.addQueryData("isBlack", "1");
		query.addQueryData("nameStr", nameStr);
		query.addQueryData("phoneStr", phoneStr);
		query.addQueryData("cardStr", cardStr);
		if (StringUtil.isNotBlank(phoneStr) && phoneStr.length() == 13) {
			query.addQueryData("phone", phoneStr);
		}
		if (StringUtil.isNotBlank(cardStr) && (cardStr.length() == 18 || cardStr.length() == 15)) {
			query.addQueryData("customerCard", cardStr);
		}
		PageResult<CustomerInfo> pageList = customerInfoService.findBlackPage(query);
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

	@RequestMapping(value="/user/applySystemBlacklist/page")
	public String applySystemPage(Long id, Model model, HttpServletRequest request) throws Exception {
		CustomerInfo customerInfo = customerInfoService.findById(id);
		Assert.isTrue(customerInfo.getUserRowId() != null
				&& !getUser(request).getId().equals(customerInfo.getUserRowId()), "非法操作");
		Assert.isTrue(!customerInfo.getIsBlack().equals(1), "不在用户黑名单中");
		if (StringUtil.isNotBlank(customerInfo.getImageUrl())) {
			customerInfo.setImageUrl(customerInfo.getImageUrl().replaceAll("/mnt/facepics", urlConfig.getImgPath()));
		}
		model.addAttribute("param", customerInfo);
		model.addAttribute(Constants.SUCCESS, true);
		return "/page/blackList/user/applySystemPage";
	}

	@RequestMapping(value="/user/applySystemBlacklist")
	@ResponseBody
	public Map<String, Object> applySystemBlacklist(Long id, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			Assert.isTrue(true, "禁止申请系统黑名单");
			CustomerInfo customerInfo = customerInfoService.findById(id);
			Assert.isTrue(customerInfo.getUserRowId() != null
					&& !getUser(request).getId().equals(customerInfo.getUserRowId()), "非法操作");
			SysBlacklistApply sysBlacklistApply = new SysBlacklistApply();
//			sysBlacklistApply.setCreateTime(new Date());
//			sysBlacklistApply.setCustomerId(customerInfo.getId());
//			sysBlacklistApply.setState(0);
//			sysBlacklistApply.setUserId(getUser(request).getId());
			sysBlacklistApplyService.save(sysBlacklistApply);
			map.put(Constants.SUCCESS, true);
			map.put(Constants.MESSAGE, "等待审核");
		} catch (Exception e) {
			commonError(logger, e,"申请经侦黑名单异常",map);
		}
		return map;
	}
	
	@RequestMapping("/system")
	public String systemList(PaginationQuery query,Model model, HttpServletRequest request, String nameStr, String phoneStr, String cardStr) throws Exception {
		query.addQueryData("nameStr", nameStr);
		query.addQueryData("phoneStr", phoneStr);
		query.addQueryData("cardStr", cardStr);
		query.addQueryData("isBlack", "2");
//		PageResult<CustomerInfo> pageList = customerInfoService.findSystemBlackPage(query);
//		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		return "/page/blackList/system/list";
	}

	@RequestMapping(value="/system/find")
	public String systemFind(Long id, Model model, HttpServletRequest request){
		try {
			// TODO zzq
//			id = 6081L;
			CustomerInfo customerInfo = customerInfoService.findById(id);
			Assert.isTrue(!customerInfo.getIsBlack().equals(2), "用户不在经侦黑名单中");
			if (StringUtil.isNotBlank(customerInfo.getImageUrl())) {
				customerInfo.setImageUrl(customerInfo.getImageUrl().replaceAll("/mnt/facepics", urlConfig.getImgPath()));
			}
//			Assert.isTrue(customerInfo.getUserRowId() != null
//					&& !getUser(request).getId().equals(customerInfo.getUserRowId()), "非法操作");

			BlacklistDetails blacklistDetails = blacklistDetailsService.findById(customerInfo.getId());
			model.addAttribute("param", customerInfo);
			model.addAttribute("blacklistDetails", blacklistDetails);
			model.addAttribute(Constants.SUCCESS, true);
			return "/page/blackList/system/detail";
		} catch (Exception e) {
			commonError(logger, e, "异常", model);
			return "redirect:/blackList/system";
		}
	}

	@RequestMapping("/system/listJson")
	@ResponseBody
	public Map<String, Object> systemListJson(PaginationQuery query,Model model, HttpServletRequest request, String nameStr, String phoneStr, String cardStr, Integer page, Integer limit) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		this.formatPageQuery(query, page, limit);
		query.addQueryData("isBlack", "2");
		query.addQueryData("nameStr", nameStr);
		query.addQueryData("phoneStr", phoneStr);
		query.addQueryData("cardStr", cardStr);
		if (StringUtil.isNotBlank(phoneStr) && phoneStr.length() == 13) {
			query.addQueryData("phone", phoneStr);
		}
		if (StringUtil.isNotBlank(cardStr) && (cardStr.length() == 18 || cardStr.length() == 15)) {
			query.addQueryData("customerCard", cardStr);
		}
		PageResult<CustomerInfo> pageList = customerInfoService.findPage(query);
		map.put("data", pageList.getRows());
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", pageList.getRowCount());
		map.put("query", query.getQueryData());
		return map;
	}



	@RequestMapping("/newBlack/list")
	public String list(PaginationQuery query,Model model, HttpServletRequest request, String nameStr, String phoneStr, String cardStr) throws Exception {
		query.addQueryData("userRowId", getUser(request).getId().toString());
		query.addQueryData("isBlack", "0");
		query.addQueryData("nameStr", nameStr);
		query.addQueryData("phoneStr", phoneStr);
		query.addQueryData("cardStr", cardStr);
		model.addAttribute("query", query.getQueryData());
		return "/page/blackList/new/list";
	}

	@RequestMapping("/newBlack/list/listJson")
	@ResponseBody
	public Map<String, Object> listJson(PaginationQuery query,Model model, HttpServletRequest request, String nameStr, String phoneStr, String cardStr, Integer page, Integer limit) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		SysUser sysUser = getUser(request);
		this.formatPageQuery(query, page, limit);
		query.addQueryData("userRowId", sysUser.getId().toString());
		query.addQueryData("nameStr", nameStr);
		query.addQueryData("isBlack", "0");
		query.addQueryData("phoneStr", phoneStr);
		query.addQueryData("cardStr", cardStr);
		if (StringUtil.isNotBlank(phoneStr) && phoneStr.length() == 13) {
			query.addQueryData("phone", phoneStr);
		}
		if (StringUtil.isNotBlank(cardStr) && (cardStr.length() == 18 || cardStr.length() == 15)) {
			query.addQueryData("customerCard", cardStr);
		}
		PageResult<CustomerInfo> pageList = customerInfoService.findPage(query);
		PaginationQuery query1 = new PaginationQuery();
		pageList.getRows().forEach(customerInfo -> {
			query1.addQueryData("userRowId", sysUser.getId().toString());
			query1.addQueryData("status", "0");
			query1.addQueryData("customerRowId", customerInfo.getId().toString());
			PageResult<SysBlacklistApply> pageResult = sysBlacklistApplyService.findPage(query1);
			customerInfo.setApplyStatus(pageResult.getRowCount() > 0 ? 1 : 0);
		});
		map.put("data", pageList.getRows());
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", pageList.getRowCount());
		map.put("query", query.getQueryData());
		map.put("areaMap", AreaConstants.AreaJsonStr);
		return map;
	}

	@RequestMapping(value="/newBlack/applyUserBlacklist/page")
	public String applyUserPage(Long id, Model model, HttpServletRequest request) throws Exception {
		CustomerInfo customerInfo = customerInfoService.findById(id);
		Assert.isTrue(customerInfo.getUserRowId() != null
				&& !getUser(request).getId().equals(customerInfo.getUserRowId()), "非法操作");
		Assert.isTrue(!customerInfo.getIsBlack().equals(0), "已经在黑名单中");
		if (StringUtil.isNotBlank(customerInfo.getImageUrl())) {
			customerInfo.setImageUrl(customerInfo.getImageUrl().replaceAll("/mnt/facepics", urlConfig.getImgPath()));
		}
		model.addAttribute("param", customerInfo);
		model.addAttribute(Constants.SUCCESS, true);
		return "/page/blackList/user/applyUserPage";
	}

	@RequestMapping(value="/newBlack/applyUserBlacklist")
	@ResponseBody
	public Map<String, Object> applyUserBlacklist(Long id, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {
			CustomerInfo customerInfo = customerInfoService.findById(id);
			Assert.isTrue(customerInfo.getUserRowId() != null
					&& !getUser(request).getId().equals(customerInfo.getUserRowId()), "非法操作");
			SysBlacklistApply sysBlacklistApply = new SysBlacklistApply();
			sysBlacklistApply.setCreateDate(new Date());
			sysBlacklistApply.setCustomerRowId(customerInfo.getId());
			sysBlacklistApply.setStatus(0);
			sysBlacklistApply.setUserRowId(getUser(request).getId());
			sysBlacklistApplyService.save(sysBlacklistApply);
			map.put(Constants.SUCCESS, true);
			map.put(Constants.MESSAGE, "等待审核");
		} catch (Exception e) {
			commonError(logger, e,"申请用户黑名单异常",map);
		}
		return map;
	}
	
}


