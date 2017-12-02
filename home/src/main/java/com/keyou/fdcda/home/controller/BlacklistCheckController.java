package com.keyou.fdcda.home.controller;

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
import com.keyou.fdcda.api.service.SysUserService;
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
import java.util.*;

@Controller
@RequestMapping("/sysBlackCheck")
public class BlacklistCheckController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(BlacklistCheckController.class);
	
	@Autowired
	private CustomerInfoService customerInfoService;
	@Autowired
	private SysBlacklistApplyService sysBlacklistApplyService;
	@Autowired
	private BlacklistDetailsService blacklistDetailsService;
	@Autowired
	private SysUserService sysUserService;

	@RequestMapping
	public String list() throws Exception {
		return "/page/sysBlackCheck/list";
	}

	@RequestMapping(value="/find")
	public String userFind(Long id, Model model, HttpServletRequest request){
		try {
			SysBlacklistApply sysBlacklistApply = sysBlacklistApplyService.findById(id);
			Assert.isTrue(!sysBlacklistApply.getStatus().equals(0), "状态不正确");
			if (StringUtil.isNotBlank(sysBlacklistApply.getFileUrl())) {
				String[] urls = sysBlacklistApply.getFileUrl().split(";");
				List<String> urlList = new ArrayList<>();
				urlList.addAll(Arrays.asList(urls));
				sysBlacklistApply.setFileUrlList(urlList);
			}
			model.addAttribute("param", sysBlacklistApply);
			model.addAttribute(Constants.SUCCESS, true);
			return "/page/sysBlackCheck/update";
		} catch (Exception e) {
			commonError(logger, e, "异常", model);
			return "redirect:/sysBlackCheck";
		}
	}

	@RequestMapping("/listJson")
	@ResponseBody
	public Map<String, Object> listJson(PaginationQuery query,Model model, HttpServletRequest request, Integer page, Integer limit) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		this.formatPageQuery(query, page, limit);
		query.addQueryData("userRowId", getUser(request).getId().toString());
		query.addQueryData("status", "0");
		PageResult<SysBlacklistApply> pageList = sysBlacklistApplyService.findPage(query);
		pageList.getRows().forEach(sysBlacklistApply -> {
			SysUser user = sysUserService.findById(sysBlacklistApply.getUserRowId());
			CustomerInfo customerInfo = customerInfoService.findById(sysBlacklistApply.getCustomerRowId());
			if (user != null) {
				sysBlacklistApply.setUserName(user.getUsername());
			}
			if (customerInfo != null) {
				sysBlacklistApply.setCustomerName(customerInfo.getName());
				sysBlacklistApply.setCustomerPhone(customerInfo.getPhone());
			}
		});
		map.put("data", pageList.getRows());
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", pageList.getRowCount());
		map.put("query", query.getQueryData());
		return map;
	}


	@RequestMapping("/check")
	@ResponseBody
	public Map<String, Object> check(PaginationQuery query,Model model, HttpServletRequest request, String nameStr, String phoneStr, Integer page, Integer limit) throws Exception {
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



}


