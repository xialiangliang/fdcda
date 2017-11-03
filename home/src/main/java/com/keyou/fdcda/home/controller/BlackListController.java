package com.keyou.fdcda.home.controller;

import com.keyou.fdcda.api.constants.AreaConstants;
import com.keyou.fdcda.api.model.CustomerInfo;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.CustomerInfoService;
import com.keyou.fdcda.home.controller.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/blackList")
public class BlackListController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(BlackListController.class);
	
	@Autowired
	private CustomerInfoService customerInfoService;
	
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
		return "/page/customerInfo/list";
	}
	
	@RequestMapping("/system")
	public String systemList(PaginationQuery query,Model model, HttpServletRequest request) throws Exception {
		PageResult<CustomerInfo> pageList = customerInfoService.findSystemBlackPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		model.addAttribute("countryMap", AreaConstants.countryMap);
		model.addAttribute("provinceMap", AreaConstants.provinceMap);
		model.addAttribute("cityMap", AreaConstants.cityMap);
		return "/page/customerInfo/list";
	}
	
}


