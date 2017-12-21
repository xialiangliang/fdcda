package com.keyou.fdcda.home.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.keyou.fdcda.api.model.SysResource;
import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.service.SysResourceService;
import com.keyou.fdcda.api.service.SysRoleinfoService;
import com.keyou.fdcda.api.utils.GsonUtil;
import com.keyou.fdcda.api.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
		
import com.keyou.fdcda.api.constants.Constants;
import com.keyou.fdcda.api.model.SysRole;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SysRoleService;
import com.keyou.fdcda.home.controller.base.BaseController;

@Controller
@RequestMapping("/sysRole")
public class SysRoleController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(SysRoleController.class);
	
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysResourceService sysResourceService;
	@Autowired
	private SysRoleinfoService sysRoleinfoService;
	
	@RequestMapping(value="/new")
	public String add() throws Exception {		
		return "/page/sysRole/new";
	}
	
	@RequestMapping(value="/find")	
	public String find(Long id, Model model){
		try {
			SysRole sysRole = sysRoleService.findById(id);
			model.addAttribute("param", sysRole);
			model.addAttribute(Constants.SUCCESS, true);
			return "/page/sysRole/update";
		} catch (Exception e) {
			commonError(logger, e, "修改跳转异常", model);
			return "/page/sysRole";
		}
	}

	@RequestMapping(value="/getResourceWithAuth")
	@ResponseBody
	public List<Map> getResourceWithAuth(HttpServletRequest request) throws Exception {
		List<Map> result = new ArrayList<>();
		try {
			String id = request.getParameter("id");
			Map<String, Object> query = new HashMap<>();
			query.put("roleId", id);
			result = sysResourceService.getTopologicalResourceJsonData(query);
		} catch (Exception e) {
			logger.error("", e);
		}
		return result;
	}
	
	@RequestMapping(value="/save")
	@ResponseBody
	public Map<String, Object> save(@ModelAttribute("sysRole") SysRole sysRole,Model model) throws Exception {		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			sysRole.setCreateTime(new Date());
			sysRoleService.save(sysRole);
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
	public Map<String, Object> update(@ModelAttribute("sysRole") SysRole sysRole,Model model,HttpServletRequest request) throws Exception {		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String jsonStr = request.getParameter("updatedData");
			Map<String, Object> updatedDataMap = (Map<String, Object>) GsonUtil.unserialize(jsonStr, HashMap.class);
			sysRoleService.update(sysRole);
			sysRoleinfoService.updateByMap(sysRole.getId(), updatedDataMap);
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
	public Map<String, Object> delete(Long id,Model model) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			sysRoleService.deleteById(id);
			map.put(Constants.SUCCESS, true);
            map.put(Constants.MESSAGE, "删除成功");
		} catch (Exception e) {
			commonError(logger, e,"删除异常",map); 
		}
		return map;
	}
	
	@RequestMapping
	public String list(PaginationQuery query,Model model) throws Exception {		
		PageResult<SysRole> pageList = sysRoleService.findPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		return "/page/sysRole/list";
	}

	@RequestMapping("/listJson")
	@ResponseBody
	public Map<String, Object> listJson(PaginationQuery query,HttpServletRequest request, Integer page, Integer limit) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		this.formatPageQuery(query, page, limit);
		PageResult<SysRole> pageList = sysRoleService.findPage(query);
		map.put("data", pageList.getRows());
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", pageList.getRowCount());
		map.put("query", query.getQueryData());
		return map;
	}
	
}


