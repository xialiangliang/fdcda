package com.keyou.fdcda.home.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.keyou.fdcda.api.constants.AreaConstants;
import com.keyou.fdcda.api.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.keyou.fdcda.api.constants.Constants;
import com.keyou.fdcda.api.model.CustomerInfo;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.CustomerInfoService;
import com.keyou.fdcda.home.controller.base.BaseController;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/customerInfo")
public class CustomerInfoController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(CustomerInfoController.class);
	
	@Autowired
	private CustomerInfoService customerInfoService;
	
	@RequestMapping(value="/new")
	public String add(Model model) throws Exception {		
		model.addAttribute("countryMap", AreaConstants.countryMap);
		model.addAttribute("provinceMap", AreaConstants.provinceMap);
		model.addAttribute("cityMap", AreaConstants.cityMap);
		return "/page/customerInfo/new";
	}
	
	@RequestMapping(value="/find")	
	public String find(Long id, Model model, HttpServletRequest request){
		try {	
			CustomerInfo customerInfo = customerInfoService.findById(id);
			Assert.isTrue(customerInfo.getUserRowId() != null
					&& !getUser(request).getId().equals(customerInfo.getUserRowId()), "非法操作");
			model.addAttribute("param", customerInfo);
			model.addAttribute("countryMap", AreaConstants.countryMap);
			model.addAttribute("provinceMap", AreaConstants.provinceMap);
			model.addAttribute("cityMap", AreaConstants.cityMap);
			model.addAttribute(Constants.SUCCESS, true);
			return "/page/customerInfo/update";
		} catch (Exception e) {
			commonError(logger, e, "修改跳转异常", model);
			return "/page/customerInfo";
		}
	}
	
	@RequestMapping(value="/save")
	@ResponseBody
	public Map<String, Object> save(@ModelAttribute("customerInfo") CustomerInfo customerInfo,Model model, HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Assert.isBlank(customerInfo.getName(), "姓名不能为空");
			Assert.isNull(customerInfo.getGender(), "性别不能为空");
			Assert.isBlank(customerInfo.getAddress(), "地址不能为空");
			Assert.isNull(customerInfo.getCity(), "城市不能为空");
			Assert.isNull(customerInfo.getNationality(), "国家不能为空");
			Assert.isNull(customerInfo.getProvince(), "省份不能为空");
			Assert.isNull(customerInfo.getCompanyid(), "公司不能为空");
			Assert.isTrue(!StringUtil.isPhone(customerInfo.getPhone()), "手机号不合法");
			customerInfo.setSource(0);
			customerInfo.setUserRowId(getUser(request).getId());
			customerInfo.setCreateTime(new Date());
			Assert.isNull(file, "请上传图片");
			String fileName = file.getOriginalFilename();
			if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png")) {
				Assert.isTrue(true, "图片格式不正确（jpg，png）");
			}
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			// 文件保存路径
			String filePath = "/opt/file/upload/" + EncodeUtil.md5(new Date().getTime() + RandomUtil.produceString(6)) + "." + suffix;
			File localFile = new File(filePath);
			file.transferTo(localFile);
			ImageUtil.ImageInfo imageInfo = ImageUtil.getImagePixelInfo(localFile);
			Assert.isNull(imageInfo, "图片信息获取失败，请重新上传");
			Assert.isTrue(imageInfo.getWidth() > 700 || imageInfo.getHeight() > 700, "图片长或宽不超过700像素");
			customerInfo.setImageUrl(filePath);
			customerInfoService.save(customerInfo);
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
	public Map<String, Object> update(@ModelAttribute("customerInfo") CustomerInfo customerInfo,Model model, HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CustomerInfo customerInfo1 = customerInfoService.findById(customerInfo.getId());
			Assert.isTrue(customerInfo1.getUserRowId() != null
					&& !getUser(request).getId().equals(customerInfo1.getUserRowId()), "非法操作");
			Assert.isBlank(customerInfo.getName(), "姓名不能为空");
			Assert.isNull(customerInfo.getGender(), "性别不能为空");
			Assert.isBlank(customerInfo.getAddress(), "地址不能为空");
			Assert.isNull(customerInfo.getCity(), "城市不能为空");
			Assert.isNull(customerInfo.getNationality(), "国家不能为空");
			Assert.isNull(customerInfo.getProvince(), "省份不能为空");
			Assert.isNull(customerInfo.getCompanyid(), "公司不能为空");
			Assert.isTrue(!StringUtil.isPhone(customerInfo.getPhone()), "手机号不合法");
			customerInfo.setModifyTime(new Date());
			Assert.isNull(file, "请上传图片");
			String fileName = file.getOriginalFilename();
			if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png")) {
				Assert.isTrue(true, "图片格式不正确（jpg，png）");
			}
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			// 文件保存路径
			String filePath = "/opt/file/upload/" + EncodeUtil.md5(new Date().getTime() + RandomUtil.produceString(6)) + "." + suffix;
			File localFile = new File(filePath);
			file.transferTo(localFile);
			ImageUtil.ImageInfo imageInfo = ImageUtil.getImagePixelInfo(localFile);
			Assert.isNull(imageInfo, "图片信息获取失败，请重新上传");
			Assert.isTrue(imageInfo.getWidth() > 700 || imageInfo.getHeight() > 700, "图片长或宽不超过700像素");
			customerInfo.setImageUrl(filePath);
			customerInfoService.update(customerInfo);
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
	public Map<String, Object> delete(Long id,Model model, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CustomerInfo customerInfo1 = customerInfoService.findById(id);
			Assert.isTrue(customerInfo1.getUserRowId() != null
					&& !getUser(request).getId().equals(customerInfo1.getUserRowId()), "非法操作");
			customerInfoService.deleteById(id);
			map.put(Constants.SUCCESS, true);
            map.put(Constants.MESSAGE, "删除成功");
		} catch (Exception e) {
			commonError(logger, e,"删除异常",map); 
		}
		return map;
	}
	
	@RequestMapping
	public String list(PaginationQuery query,Model model, HttpServletRequest request) throws Exception {
		query.addQueryData("userRowId", getUser(request).getId().toString());
		PageResult<CustomerInfo> pageList = customerInfoService.findPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		model.addAttribute("countryMap", AreaConstants.countryMap);
		model.addAttribute("provinceMap", AreaConstants.provinceMap);
		model.addAttribute("cityMap", AreaConstants.cityMap);
		return "/page/customerInfo/list";
	}

	@RequestMapping(value="/addToBlackList")
	@ResponseBody
	public Map<String, Object> addToBlackList(Long id, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CustomerInfo customerInfo = customerInfoService.findById(id);
			Assert.isTrue(customerInfo.getUserRowId() != null
					&& !getUser(request).getId().equals(customerInfo.getUserRowId()), "非法操作");
			customerInfo.setIsBlack(1);
			customerInfo.setModifyTime(new Date());
			customerInfoService.update(customerInfo);
			map.put(Constants.SUCCESS, true);
			map.put(Constants.MESSAGE, "添加黑名单成功");
		} catch (Exception e) {
			commonError(logger, e,"添加黑名单异常",map);
		}
		return map;
	}
	
}

