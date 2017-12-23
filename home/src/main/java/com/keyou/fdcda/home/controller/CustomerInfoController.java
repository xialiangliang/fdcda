package com.keyou.fdcda.home.controller;

import java.io.File;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Maps;
import com.keyou.fdcda.api.model.OrderEvaluate;
import com.keyou.fdcda.api.model.OrderInfo;
import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.service.OrderEvaluateService;
import com.keyou.fdcda.api.service.OrderInfoService;
import com.keyou.fdcda.api.service.SysUserService;
import com.keyou.fdcda.api.utils.*;
import com.keyou.fdcda.api.utils.config.UrlConfig;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.keyou.fdcda.api.constants.AreaConstants;
import com.keyou.fdcda.api.constants.Constants;
import com.keyou.fdcda.api.constants.ImageInfoConstants;
import com.keyou.fdcda.api.model.CustomerInfo;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.CustomerInfoService;
import com.keyou.fdcda.home.controller.base.BaseController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customerInfo")
public class CustomerInfoController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(CustomerInfoController.class);
	
	@Autowired
	private CustomerInfoService customerInfoService;
	@Autowired
	private UrlConfig urlConfig;
	@Autowired
	private OrderEvaluateService orderEvaluateService;
	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private SysUserService sysUserService;


	@RequestMapping(value="/manage")
	public String page(Model model) throws Exception {
		return "redirect:/customerInfo";
	}
	
	@RequestMapping(value="/new")
	public String add(Model model) throws Exception {		
		model.addAttribute("countryMap", AreaConstants.countryMap);
		model.addAttribute("provinceMap", AreaConstants.provinceMap);
		model.addAttribute("cityMap", AreaConstants.cityMap);
		model.addAttribute("areaMap", AreaConstants.AreaJsonStr);
		return "/page/customerInfo/new";
	}

	@RequestMapping(value="/batchnew")
	public String batchnew(Model model) throws Exception {
		return "/page/customerInfo/batchnew";
	}

	@RequestMapping(value="/batchnew/save")
	@ResponseBody
	public Map<String, Object> batchSave(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (file != null) {
				String fileName = file.getOriginalFilename();
				if (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls")) {
					Assert.isTrue(true, "格式不正确（xlsx，xls）");
				}
				String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
				// 文件保存路径
				String filePath = ImageInfoConstants.UPLOAD_TEMP_FILE_BASE_PATH + EncodeUtil.md5(new Date().getTime() + RandomUtil.produceString(6)) + "." + suffix;
				File localFile = new File(filePath);
				if (!localFile.getParentFile().exists()) {
					localFile.getParentFile().mkdirs();
				}
				file.transferTo(localFile);
				BigDecimal sum = BigDecimal.ZERO;
				List<String[]> userList = ExcelUtil.readExcel(filePath, "Sheet1", 1, 11);
				if (userList == null || userList.isEmpty()) {
					throw new Exception("上传的文件是空文件，请修改后重新上传!");
				}
				userList.remove(0);

				for (String[] userStrs : userList) {
					CustomerInfo customerInfo = new CustomerInfo();
					customerInfo.setIsBlack(0);
					customerInfo.setIsVip(0);
					customerInfo.setValidFlag(1);
					customerInfo.setCreateTime(new Date());
					customerInfo.setSource(0);
					customerInfo.setUserRowId(getUser(request).getId());
					customerInfo.setName(userStrs[0]);
					customerInfo.setPhone(userStrs[1]);
					Integer gender = null;
					if (StringUtil.equals(userStrs[2], "男")) {
						gender = 0;
					} else if (StringUtil.equals(userStrs[2], "女")) {
						gender = 1;
					}
					customerInfo.setGender(gender);
					customerInfo.setNationality(userStrs[3]);
					customerInfo.setProvince(userStrs[4]);
					customerInfo.setCity(userStrs[5]);
					customerInfo.setAddress(userStrs[6]);
					customerInfo.setQq(userStrs[7]);
					customerInfo.setWeixin(userStrs[8]);
					customerInfo.setCustomerCard(userStrs[9]);
					customerInfo.setCompanyName(userStrs[10]);
					customerInfoService.save(customerInfo);
				}
			}
			map.put(Constants.SUCCESS, true);
			map.put(Constants.MESSAGE, "添加成功");

		} catch (Exception e) {
			commonError(logger, e, "添加异常",map);
		}
		return map;
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public String directedredDown(Model model, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename=" + new String(("上传模板_" + new Date().getTime() + ".xlsx").getBytes("GB2312"), "ISO8859-1"));
			response.setContentType("application/x-excel");
			OutputStream os = response.getOutputStream();

			SXSSFWorkbook wb = new SXSSFWorkbook();
			Sheet sheet = wb.createSheet("Sheet1");
			//创建第一个sheet     
			//生成第一行     
			Row row = sheet.createRow(0);
			//给这一行的第一列赋值     
			row.createCell(0).setCellValue("姓名");
			row.createCell(1).setCellValue("手机");
			row.createCell(2).setCellValue("性别");
			row.createCell(3).setCellValue("国籍");
			row.createCell(4).setCellValue("省份");
			row.createCell(5).setCellValue("地市");
			row.createCell(6).setCellValue("联系地址");
			row.createCell(7).setCellValue("QQ");
			row.createCell(8).setCellValue("微信");
			row.createCell(9).setCellValue("身份证");
			row.createCell(10).setCellValue("单位");
			wb.write(os);
			os.close();
			wb.close();
			redirectAttributes.addFlashAttribute(Constants.SUCCESS, true);
			redirectAttributes.addFlashAttribute(Constants.MESSAGE, "操作成功！");
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
	
	@RequestMapping(value="/find")	
	public String find(Long id, Model model, HttpServletRequest request){
		try {	
			CustomerInfo customerInfo = customerInfoService.findById(id);
			Assert.isTrue(customerInfo.getUserRowId() != null
					&& !getUser(request).getId().equals(customerInfo.getUserRowId()), "非法操作");
			if (StringUtil.isNotBlank(customerInfo.getImageUrl())) {
				customerInfo.setImageUrl(customerInfo.getImageUrl().replaceAll("/mnt/facepics", urlConfig.getImgPath()));
			}
			model.addAttribute("param", customerInfo);
			model.addAttribute("countryMap", AreaConstants.countryMap);
			model.addAttribute("provinceMap", AreaConstants.provinceMap);
			model.addAttribute("cityMap", AreaConstants.cityMap);
			model.addAttribute("areaMap", AreaConstants.AreaJsonStr);
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
			customerInfo.setValidFlag(1);
			Assert.isBlank(customerInfo.getName(), "姓名不能为空");
			Assert.isNull(customerInfo.getGender(), "性别不能为空");
			Assert.isBlank(customerInfo.getAddress(), "地址不能为空");
			Assert.isNull(customerInfo.getCity(), "城市不能为空");
			Assert.isNull(customerInfo.getNationality(), "国家不能为空");
			Assert.isNull(customerInfo.getProvince(), "省份不能为空");
			Assert.isBlank(customerInfo.getCompanyName(), "公司不能为空");
			Assert.isTrue(!StringUtil.isPhone(customerInfo.getPhone()), "手机号不合法");
			customerInfo.setSource(0);
			customerInfo.setUserRowId(getUser(request).getId());
			customerInfo.setCreateTime(new Date());
//			Assert.isNull(file, "请上传图片");
			if (file != null) {
				String fileName = file.getOriginalFilename();
				if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png")) {
					Assert.isTrue(true, "图片格式不正确（jpg，png）");
				}
				String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
				// 文件保存路径
				String filePath = ImageInfoConstants.UPLOAD_IMAGE_FILE_BASE_PATH + EncodeUtil.md5(new Date().getTime() + RandomUtil.produceString(6)) + "." + suffix;
				File localFile = new File(filePath);
				file.transferTo(localFile);
				ImageUtil.ImageInfo imageInfo = ImageUtil.getImagePixelInfo(localFile);
				Assert.isNull(imageInfo, "图片信息获取失败，请重新上传");
				Assert.isTrue(imageInfo.getWidth() > 700 || imageInfo.getHeight() > 700, "图片长或宽不超过700像素");
				customerInfo.setImageUrl(filePath);
				customerInfoService.updateImageBaseSend(customerInfo.getId(), customerInfo.getImageUrl(), 0);
			}
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
	public Map<String, Object> update(@ModelAttribute("customerInfo") CustomerInfo customerInfo,Model model, HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {		
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
			Assert.isNull(customerInfo.getCompanyName(), "公司不能为空");
			Assert.isTrue(!StringUtil.isPhone(customerInfo.getPhone()), "手机号不合法");
			customerInfo.setModifyTime(new Date());
//			Assert.isNull(file, "请上传图片");
			if (file != null) {
				String fileName = file.getOriginalFilename();
				if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png")) {
					Assert.isTrue(true, "图片格式不正确（jpg，png）");
				}
				String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
				// 文件保存路径
				String filePath = ImageInfoConstants.UPLOAD_IMAGE_FILE_BASE_PATH + EncodeUtil.md5(new Date().getTime() + RandomUtil.produceString(6)) + "." + suffix;
				File localFile = new File(filePath);
				file.transferTo(localFile);
				ImageUtil.ImageInfo imageInfo = ImageUtil.getImagePixelInfo(localFile);
				Assert.isNull(imageInfo, "图片信息获取失败，请重新上传");
				Assert.isTrue(imageInfo.getWidth() > 700 || imageInfo.getHeight() > 700, "图片长或宽不超过700像素");
				customerInfo.setImageUrl(filePath);
				customerInfoService.updateImageBaseSend(customerInfo.getId(), customerInfo.getImageUrl(), 1);
			}
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
	public String list(PaginationQuery query,Model model, HttpServletRequest request, String nameStr, String phoneStr) throws Exception {
		query.addQueryData("userRowId", getUser(request).getId().toString());
		query.addQueryData("nameStr", nameStr);
		query.addQueryData("phoneStr", phoneStr);
		if (StringUtil.isNotBlank(phoneStr) && phoneStr.length() == 13) {
			query.addQueryData("phone", phoneStr);
		}
		model.addAttribute("query", query.getQueryData());
		return "/page/customerInfo/list";
	}

	@RequestMapping("/listJson")
	@ResponseBody
	public Map<String, Object> listJson(PaginationQuery query,Model model, HttpServletRequest request, String nameStr, String phoneStr, Integer page, Integer limit) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		this.formatPageQuery(query, page, limit);
		query.addQueryData("userRowId", getUser(request).getId().toString());
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
		map.put("areaMap", AreaConstants.AreaJsonStr);
		return map;
	}

	@RequestMapping("/vipList")
	public String vipList(PaginationQuery query,Model model, HttpServletRequest request, String nameStr, String phoneStr) throws Exception {
		query.addQueryData("userRowId", getUser(request).getId().toString());
		query.addQueryData("nameStr", nameStr);
		query.addQueryData("phoneStr", phoneStr);
		if (StringUtil.isNotBlank(phoneStr) && phoneStr.length() == 13) {
			query.addQueryData("phone", phoneStr);
		}
		model.addAttribute("query", query.getQueryData());
		return "/page/customerInfo/vipList";
	}

	@RequestMapping("/vipListJson")
	@ResponseBody
	public Map<String, Object> vipListJson(PaginationQuery query,Model model, HttpServletRequest request, String nameStr, String phoneStr, Integer page, Integer limit) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		this.formatPageQuery(query, page, limit);
		query.addQueryData("userRowId", getUser(request).getId().toString());
		query.addQueryData("nameStr", nameStr);
		query.addQueryData("phoneStr", phoneStr);
		query.addQueryData("isVip", "1");
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
		map.put("areaMap", AreaConstants.AreaJsonStr);
		return map;
	}

	@RequestMapping(value="/rmVip")
	@ResponseBody
	public Map<String, Object> rmVip(Long id, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CustomerInfo customerInfo = customerInfoService.findById(id);
			Assert.isTrue(customerInfo.getUserRowId() != null
					&& !getUser(request).getId().equals(customerInfo.getUserRowId()), "非法操作");
			customerInfo.setIsVip(0);
			customerInfo.setModifyTime(new Date());
			customerInfoService.update(customerInfo);
			map.put(Constants.SUCCESS, true);
			map.put(Constants.MESSAGE, "移除VIP成功");
		} catch (Exception e) {
			commonError(logger, e,"移除VIP异常",map);
		}
		return map;
	}

	@RequestMapping(value="/addVip")
	@ResponseBody
	public Map<String, Object> addVip(Long id, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CustomerInfo customerInfo = customerInfoService.findById(id);
			Assert.isTrue(customerInfo.getUserRowId() != null
					&& !getUser(request).getId().equals(customerInfo.getUserRowId()), "非法操作");
			customerInfo.setIsVip(1);
			customerInfo.setModifyTime(new Date());
			customerInfoService.update(customerInfo);
			map.put(Constants.SUCCESS, true);
			map.put(Constants.MESSAGE, "加入VIP成功");
		} catch (Exception e) {
			commonError(logger, e,"加入VIP异常",map);
		}
		return map;
	}

	@RequestMapping(value="/addToBlackList")
	@ResponseBody
	public Map<String, Object> addToBlackList(Long id, String reason, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CustomerInfo customerInfo = customerInfoService.findById(id);
			Assert.isTrue(customerInfo.getUserRowId() != null
					&& !getUser(request).getId().equals(customerInfo.getUserRowId()), "非法操作");
			customerInfo.setIsBlack(1);
			customerInfo.setBlackReason(reason);
			customerInfo.setBlackTime(new Date());
			customerInfo.setModifyTime(new Date());
			customerInfoService.update(customerInfo);
			map.put(Constants.SUCCESS, true);
			map.put(Constants.MESSAGE, "添加黑名单成功");
		} catch (Exception e) {
			commonError(logger, e,"添加黑名单异常",map);
		}
		return map;
	}

	@RequestMapping(value="/detail")
	public String detail(Long id, Model model, HttpServletRequest request){
		try {
			CustomerInfo customerInfo = customerInfoService.findById(id);
			Assert.isTrue(customerInfo.getUserRowId() != null
					&& !getUser(request).getId().equals(customerInfo.getUserRowId()), "非法操作");
			if (StringUtil.isNotBlank(customerInfo.getImageUrl())) {
				customerInfo.setImageUrl(customerInfo.getImageUrl().replaceAll("/mnt/facepics", urlConfig.getImgPath()));
			}
			List<Long> customerIds = customerInfoService.findRealCustomerIdBySingleId(id);
			List<OrderEvaluate> evaluateList = orderEvaluateService.findListByCustomerIds(customerIds);
			evaluateList.forEach(orderEvaluate -> {
				if (StringUtil.isNotBlank(orderEvaluate.getImagesUrl())) {
					String[] urls = orderEvaluate.getImagesUrl().split(",");
					orderEvaluate.setImagesUrlList(Arrays.asList(urls));
				}
				OrderInfo orderInfo = orderInfoService.findById(orderEvaluate.getOrderRowId());
				if (orderInfo != null) {
					SysUser user = sysUserService.findById(orderInfo.getUserRowId());
					if (user != null) {
						orderEvaluate.setEvaluateName(StringUtil.hideName(user.getUsername()));
					}
				}
			});
			model.addAttribute("param", customerInfo);
			model.addAttribute("evaluateList", GsonUtil.serialize(evaluateList));
			model.addAttribute(Constants.SUCCESS, true);
			return "/page/customerInfo/detail";
		} catch (Exception e) {
			commonError(logger, e, "修改跳转异常", model);
			return "/page/customerInfo";
		}
	}
	
}


