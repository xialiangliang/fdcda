package com.keyou.fdcda.home.controller;

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

import com.keyou.fdcda.api.constants.Constants;
import com.keyou.fdcda.api.constants.ImageInfoConstants;
import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.model.VisitRecordInfo;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.VisitRecordInfoService;
import com.keyou.fdcda.api.utils.StringUtil;
import com.keyou.fdcda.home.controller.base.BaseController;

@Controller
@RequestMapping("/visitRecordInfo")
public class VisitRecordInfoController extends BaseController {
	private static Logger log = Logger.getLogger(VisitRecordInfoController.class);

	@Autowired
	private VisitRecordInfoService visitRecordInfoService;

	@RequestMapping(value = "/new")
	public String add() throws Exception {
		return "/visitRecordInfo/new";
	}

	@RequestMapping(value = "/find")
	public String find(Long id, Model model) {
		try {
			VisitRecordInfo visitRecordInfo = visitRecordInfoService.findById(id);
			model.addAttribute("param", visitRecordInfo);
			model.addAttribute(Constants.SUCCESS, true);
			return "/visitRecordInfo/update";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(Constants.SUCCESS, false);
			model.addAttribute(Constants.MESSAGE, e.getMessage());
			return "/visitRecordInfo/update";
		}
	}

	@RequestMapping(value = "/save")
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

	@RequestMapping(value = "/update")
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

	@RequestMapping(value = "/delete")
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

	@RequestMapping(method = RequestMethod.GET)
	public String list(PaginationQuery query, Model model) throws Exception {
		PageResult<VisitRecordInfo> pageList = visitRecordInfoService.findPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		return "/visitRecordInfo/list";
	}
	
	private void dealUrl(List<VisitRecordInfo> list){
		if (list != null && !list.isEmpty()) {
			for (VisitRecordInfo info : list) {
				Integer type = info.getVisitType();
				if (type != null&&StringUtil.isNotBlank(info.getImageUrl())) {
					String url = info.getImageUrl();
					info.setImageUrl(ImageInfoConstants.STATIC_IMAGE_SERVER_URL+url.substring(url.indexOf("deal")));
					 
				}
			}
		}
	}

	@RequestMapping(value = "/visitIndex",method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) throws Exception {
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
		
		// 未识别普通访客
	    map.put("visitType", ImageInfoConstants.VISIT_TYPE_0);
	    Long normalNotCount = visitRecordInfoService.findPageCount(map);
		
	    // 可疑人员数
	    map.remove("visitType");
		map.put("visitTypeblack", "1");
		Long blackCount = visitRecordInfoService.findPageCount(map);
		 map.remove("visitTypeblack");
	    
		map.put("endRecord", 10);
		map.put("startRecord", 0);
		// 会员数
		map.put("visitType", ImageInfoConstants.VISIT_TYPE_2);
		List<VisitRecordInfo> vipList = visitRecordInfoService.findAllPage(map);
		// 已识别普通访客
		map.put("visitType", ImageInfoConstants.VISIT_TYPE_1);
	    List<VisitRecordInfo> normalList = visitRecordInfoService.findAllPage(map);
	    
	    // 未识别普通访客
	    map.put("visitType", ImageInfoConstants.VISIT_TYPE_0);
	    List<VisitRecordInfo> normalNotList = visitRecordInfoService.findAllPage(map);
		
		// 可疑人员数
	    map.remove("visitType");
		map.put("visitTypeblack", "1");
		List<VisitRecordInfo> blackList = visitRecordInfoService.findAllPage(map);
		

		dealUrl(blackList);
		dealUrl(vipList);
		dealUrl(normalList);
		dealUrl(normalNotList);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("blackCount", blackCount);
		model.addAttribute("blackList", blackList);
		model.addAttribute("vipCount", vipCount);
		model.addAttribute("normalList", normalList);
		model.addAttribute("normalCountt", normalCount);
		model.addAttribute("vipList", vipList);
		model.addAttribute("normalNotList", normalNotList);
		model.addAttribute("normalNotCount", normalNotCount);
		
		return "/page/index";
	}
}
