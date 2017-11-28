package com.keyou.fdcda.home.controller;

import java.util.ArrayList;
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

	@RequestMapping(value = "/visitIndex",method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) throws Exception {
		SysUser sysUser = getUser(request);
		if (sysUser == null) {
			return "redirect:/login";
		}
		Map<String, Object> map = new HashMap<>();
		map.put("createToday", "1");
		map.put("userRowId", sysUser.getId());
		List<VisitRecordInfo> list = visitRecordInfoService.findAllPage(map);
		// 总访问量
		Integer totalCount = 0;
		// 可疑人员数
		List<VisitRecordInfo> blackList = new ArrayList<>();

		// 会员数
		List<VisitRecordInfo> vipList = new ArrayList<>();
		// 普通访客
		List<VisitRecordInfo> normalList = new ArrayList<>();

		if (list != null && !list.isEmpty()) {
			totalCount = list.size();
			for (VisitRecordInfo info : list) {
				Integer type = info.getVisitType();
				if (type != null) {
					switch (type) {
					case ImageInfoConstants.VISIT_TYPE_0:
						normalList.add(info);
						break;
					case ImageInfoConstants.VISIT_TYPE_1:
						normalList.add(info);
						break;
					case ImageInfoConstants.VISIT_TYPE_2:
						vipList.add(info);
						break;
					case ImageInfoConstants.VISIT_TYPE_3:
						blackList.add(info);
						break;
					case ImageInfoConstants.VISIT_TYPE_4:
						blackList.add(info);
						break;
					case ImageInfoConstants.VISIT_TYPE_5:
						vipList.add(info);
						break;
						
					default:
						break;
					}
				}
			}
		}
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("blackCount", blackList.size());
		model.addAttribute("vipCount", vipList.size());
		model.addAttribute("normalList", normalList);
		model.addAttribute("vipList", vipList);
		model.addAttribute("totalList", list);
		
		return "/page/index";
	}
}
