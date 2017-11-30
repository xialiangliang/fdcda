package com.keyou.fdcda.home.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.keyou.fdcda.api.constants.Constants;
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
	
	
}
