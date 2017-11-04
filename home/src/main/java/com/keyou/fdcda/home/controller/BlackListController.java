package com.keyou.fdcda.home.controller;

import com.keyou.fdcda.api.constants.AreaConstants;
import com.keyou.fdcda.api.constants.Constants;
import com.keyou.fdcda.api.model.CustomerInfo;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.CustomerInfoService;
import com.keyou.fdcda.api.utils.Assert;
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

	@RequestMapping
	public String list() throws Exception {
		return "redirect:/blackList/user";
	}
	
	@RequestMapping("/user")
	public String userList(PaginationQuery query,Model model, HttpServletRequest request) throws Exception {
		query.addQueryData("userRowId", getUser(request).getId().toString());
		query.addQueryData("isBlack", "1");
		PageResult<CustomerInfo> pageList = customerInfoService.findPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		model.addAttribute("countryMap", AreaConstants.countryMap);
		model.addAttribute("provinceMap", AreaConstants.provinceMap);
		model.addAttribute("cityMap", AreaConstants.cityMap);
		return "/page/blackList/user/list";
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
	public String systemList(PaginationQuery query,Model model, HttpServletRequest request) throws Exception {
		PageResult<CustomerInfo> pageList = customerInfoService.findSystemBlackPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		model.addAttribute("countryMap", AreaConstants.countryMap);
		model.addAttribute("provinceMap", AreaConstants.provinceMap);
		model.addAttribute("cityMap", AreaConstants.cityMap);
		return "/page/blackList/system/list";
	}
	
}


