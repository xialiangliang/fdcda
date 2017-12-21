package com.keyou.fdcda.home.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.keyou.fdcda.api.constants.Constants;
import com.keyou.fdcda.api.constants.ImageInfoConstants;
import com.keyou.fdcda.api.model.ImageInfo;
import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.model.VisitRecordInfo;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.CustomerInfoService;
import com.keyou.fdcda.api.service.ImageInfoService;
import com.keyou.fdcda.api.service.VisitRecordInfoService;
import com.keyou.fdcda.api.utils.DateUtil;
import com.keyou.fdcda.api.utils.StringUtil;
import com.keyou.fdcda.api.utils.config.UrlConfig;
import com.keyou.fdcda.home.controller.base.BaseController;

@Controller
@RequestMapping
public class VisitRecordInfoController extends BaseController {
	private static Logger log = Logger.getLogger(VisitRecordInfoController.class);

	@Autowired
	private VisitRecordInfoService visitRecordInfoService;

	@Autowired
	private CustomerInfoService customerInfoService;

	@Autowired
	private ImageInfoService imageInfoService;
	
	@Autowired
	private UrlConfig urlConfig;

	@RequestMapping(value = "/visitRecord")
	public String visitRecord() throws Exception {
		return "redirect:/visitRecordInfo";
	}

	@RequestMapping(value = "/visitRecordInfo/new")
	public String add() throws Exception {
		return "/visitRecordInfo/new";
	}

	@RequestMapping(value = "/visitRecordInfo/findDistinguish")
	public String findDistinguish(Long id, Model model) {
		try {
			ImageInfo info = imageInfoService.findById(id);
			String url = info.getImageUrl();
			info.setImageUrl(urlConfig.getImgPath() + "/" + url.substring(url.indexOf("deal")));
			model.addAttribute("info", info);
			model.addAttribute(Constants.SUCCESS, true);
			return "/page/customerInfo/distinguish";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(Constants.SUCCESS, false);
			model.addAttribute(Constants.MESSAGE, e.getMessage());
			return "/visitRecordInfo/update";
		}
	}

	@RequestMapping(value = "/visitRecordInfo/save")
	public String save(@ModelAttribute("visitRecordInfo") VisitRecordInfo visitRecordInfo, Model model) {
		try {
			visitRecordInfoService.save(visitRecordInfo);
			model.addAttribute(Constants.SUCCESS, true);
			return "redirect:/visitRecordInfo";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", visitRecordInfo);
			model.addAttribute(Constants.SUCCESS, false);
			model.addAttribute(Constants.MESSAGE, e.getMessage());
			return "/visitRecordInfo/new";
		}
	}

	@RequestMapping(value = "/visitRecordInfo/update")
	public String update(@ModelAttribute("visitRecordInfo") VisitRecordInfo visitRecordInfo, Model model)
			throws Exception {
		try {
			visitRecordInfoService.update(visitRecordInfo);
			model.addAttribute(Constants.SUCCESS, true);
			return "redirect:/visitRecordInfo";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", visitRecordInfo);
			model.addAttribute(Constants.SUCCESS, false);
			model.addAttribute(Constants.MESSAGE, e.getMessage());
			return "/visitRecordInfo/update";
		}
	}

	@RequestMapping(value = "/visitRecordInfo/delete")
	public String delete(Long id, Model model) throws Exception {
		try {
			visitRecordInfoService.deleteById(id);
			model.addAttribute(Constants.SUCCESS, true);
			return "redirect:/visitRecordInfo";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(Constants.SUCCESS, false);
			model.addAttribute(Constants.MESSAGE, e.getMessage());
			return "redirect:/visitRecordInfo";
		}
	}

	@RequestMapping(value = "/visitRecordInfo", method = RequestMethod.GET)
	public String list(PaginationQuery query, Model model, HttpServletRequest request,String begin,String end) throws Exception {
		SysUser sysUser = getUser(request);
		if (sysUser == null) {
			return "redirect:/login";
		}
		Map<String, Object> map = new HashMap<>();
		
		if (StringUtil.isBlank(begin) && StringUtil.isBlank(end)) {
			Date now = new Date();
			begin = DateUtil.getDate(DateUtil.addDate(now, -7), DateUtil.DATE_FORMAT);
			end = DateUtil.getDate(now, DateUtil.DATE_FORMAT);
			model.addAttribute("bingTitle", "本店最近一周访问总体情况");
			model.addAttribute("lineText", "本店最近一周访问详细情况");
		}
		else{
			model.addAttribute("bingTitle", begin+"-"+end+"本店访问总体情况");
			model.addAttribute("lineText", begin+"-"+end+"本店访问详细情况");
		}
		map.put("beginDate", begin);
		map.put("endDate", end);
		map.put("userRowId", sysUser.getId());
		 
		/**按访客类型统计总数,展现饼图*/
		List<VisitRecordInfo> dayCountReport = visitRecordInfoService.selectDayCountReport(map);
		model.addAttribute("bingData", dayCountReport);
		
		/**按天统计总数*/
		List<VisitRecordInfo> dayDetailReport = visitRecordInfoService.selectDayDetailReport(map);
		model.addAttribute("lineData", dayDetailReport);
		
		 
		return "/page/visitinfo/list";
	}
	
	
	public String list_bak(PaginationQuery query, Model model, HttpServletRequest request) throws Exception {
		SysUser sysUser = getUser(request);
		if (sysUser == null) {
			return "redirect:/login";
		}
		Map<String, Object> map = new HashMap<>();
		map.put("createToday", "1");
		map.put("userRowId", sysUser.getId());
		// 总访问量
		Long totalCount = visitRecordInfoService.findPageCount(map);
		
		map.put("visitType", ImageInfoConstants.VISIT_TYPE_2);
		Long vipCount = visitRecordInfoService.findPageCount(map);
		
		map.put("visitType", ImageInfoConstants.VISIT_TYPE_1);
		Long normalCount = visitRecordInfoService.findPageCount(map);
		
		// 新客
	    map.put("visitType", ImageInfoConstants.VISIT_TYPE_0);
	    Long normalNotCount = visitRecordInfoService.findPageCount(map);
		
	    // 黑名单数
	    map.remove("visitType");
		map.put("visitTypeblack", "1");
		Long blackCount = visitRecordInfoService.findPageCount(map);
		 map.remove("visitTypeblack");
		 
	   // 可疑人员数
	    map.remove("visitTypeblack");
		map.put("visitType", "6");
		Long keyiCount = visitRecordInfoService.findPageCount(map);
		 map.remove("visitType");
	    
		map.put("endRecord", 10);
		map.put("startRecord", 0);
		// 会员数
		map.put("visitType", ImageInfoConstants.VISIT_TYPE_2);
		List<VisitRecordInfo> vipList = visitRecordInfoService.findAllPage(map);
		// 老顾客
		map.put("visitType", ImageInfoConstants.VISIT_TYPE_1);
	    List<VisitRecordInfo> normalList = visitRecordInfoService.findAllPage(map);
	    
	    // 新客
	    map.put("visitType", ImageInfoConstants.VISIT_TYPE_0);
	    List<VisitRecordInfo> normalNotList = visitRecordInfoService.findAllPage(map);
	    
	    //可疑人员
	    map.put("visitType", ImageInfoConstants.VISIT_TYPE_6);
	    List<VisitRecordInfo> keyiList = visitRecordInfoService.findAllPage(map);
		
		// 黑名单数
	    map.remove("visitType");
		map.put("visitTypeblack", "1");
		List<VisitRecordInfo> blackList = visitRecordInfoService.findAllPage(map);
		

		dealUrl(blackList);
		dealUrl(vipList);
		dealUrl(normalList);
		dealUrl(normalNotList);
		dealUrl(keyiList);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("blackCount", blackCount);
		model.addAttribute("blackList", blackList);
		model.addAttribute("vipCount", vipCount);
		model.addAttribute("normalList", normalList);
		model.addAttribute("normalCountt", normalCount);
		model.addAttribute("vipList", vipList);
		model.addAttribute("normalNotList", normalNotList);
		model.addAttribute("normalNotCount", normalNotCount);
		
		model.addAttribute("keyiList", keyiList);
		model.addAttribute("keyiCountt", keyiCount);
		return "/page/visitinfo/list";
	}

	private void dealUrl(List<VisitRecordInfo> list) {
		if (list != null && !list.isEmpty()) {
			for (VisitRecordInfo info : list) {
				Integer type = info.getVisitType();
				if (type != null && StringUtil.isNotBlank(info.getImageUrl())) {
					String url = info.getImageUrl();
					info.setImageUrl(urlConfig.getImgPath() + "/" + url.substring(url.indexOf("deal")));

				}
			}
		}
	}
	
	@RequestMapping(value = "/yitusoutu", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findImages(  @RequestParam("uploadfile") MultipartFile file, Model model) {
        Map<String, Object> map = new HashMap<String, Object>();
        
        return map;
    }

}
