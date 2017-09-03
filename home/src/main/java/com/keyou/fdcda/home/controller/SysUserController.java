package com.keyou.fdcda.home.controller;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.keyou.fdcda.api.utils.EncodeUtil;
import com.keyou.fdcda.api.utils.Result;
import com.keyou.fdcda.api.utils.StringUtil;
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
import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.SysUserService;
import com.keyou.fdcda.home.controller.base.BaseController;

@Controller
@RequestMapping("/sysUser")
public class SysUserController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(SysUserController.class);
	
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping(value="/new")
	public String add() throws Exception {		
		return "/page/sysUser/new";
	}
	
	@RequestMapping(value="/find")	
	public String find(Long id, Model model){
		try {	
			SysUser sysUser = sysUserService.findById(id);
			model.addAttribute("param", sysUser);
			model.addAttribute(Constants.SUCCESS, true);
			return "/page/sysUser/update";
		} catch (Exception e) {
			commonError(logger, e, "修改跳转异常", model);
			return "/page/sysUser";
		}
	}

	@RequestMapping(value="/getPublicKey")
	@ResponseBody
	public Map<String, Object> getPublicKey(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<>();
		KeyPair keyPair = EncodeUtil.rsaGenKeyPair();
		String publicKeyStr = new String(Base64.getEncoder().encode(keyPair.getPublic().getEncoded()));
		request.getSession().setAttribute("privateKey", keyPair.getPrivate());
		map.put("publicKey", publicKeyStr);
		return map;
	}
	
	@RequestMapping(value="/save")
	@ResponseBody
	public Map<String, Object> save(@ModelAttribute("sysUser") SysUser sysUser,Model model, HttpServletRequest request) throws Exception {		
		Map<String, Object> map = new HashMap<>();
		map.put(Constants.SUCCESS, false);
		try {
			PrivateKey privateKey = (PrivateKey) request.getSession().getAttribute("privateKey");
			byte[] decryptedBytes = EncodeUtil.rsaDecrypt(Base64.getDecoder().decode(sysUser.getLoginpwd()), privateKey);
			sysUser.setLoginpwd(new String(decryptedBytes));
			Result<SysUser> result = sysUserService.validateNewUser(sysUser);
			if (!result.getSuccess()) {
				map.put(Constants.MESSAGE, result.getMessage());
				return map;
			}
			Result<SysUser> regResult = sysUserService.register(sysUser);
			if (!regResult.getSuccess()) {
				map.put(Constants.MESSAGE, regResult.getMessage());
				return map;
			}
			map.put(Constants.SUCCESS, true);
            map.put(Constants.MESSAGE, regResult.getMessage());
            
		} catch (Exception e) {
			commonError(logger, e, "注册异常",map); 
		}
		return map;
	}
	
	@RequestMapping(value="/update")
	@ResponseBody
	public Map<String, Object> update(@ModelAttribute("sysUser") SysUser sysUser,Model model, HttpServletRequest request) throws Exception {		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constants.SUCCESS, false);
		try {
			if (StringUtil.isNotBlank(sysUser.getPhone()) && !StringUtil.isPhone(sysUser.getPhone())) {
				map.put(Constants.MESSAGE, "非法手机号");
				return map;
			}
			if (StringUtil.isNotBlank(sysUser.getLoginname()) && !StringUtil.isLoginname(sysUser.getLoginname())) {
				map.put(Constants.MESSAGE, "非法登录名");
				return map;
			}
			try {
				sysUserService.update(sysUser);
			} catch (Exception e) {
				map.put(Constants.MESSAGE, "修改失败，可能是手机号或登录名已存在");
				return map;
			}
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
			sysUserService.deleteById(id);
			map.put(Constants.SUCCESS, true);
            map.put(Constants.MESSAGE, "删除成功");
		} catch (Exception e) {
			commonError(logger, e,"删除异常",map); 
		}
		return map;
	}
	
	@RequestMapping
	public String list(PaginationQuery query,Model model,HttpServletRequest request) throws Exception {
		String keyword = request.getParameter("keyword");
		if (StringUtil.isNotBlank(keyword)) {
			query.addQueryData("keyword", keyword);
		}
		PageResult<SysUser> pageList = sysUserService.findPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		return "/page/sysUser/list";
	}


	@RequestMapping(value="/resetPassword")
	@ResponseBody
	public Map<String, Object> resetPassword(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String userIdStr = request.getParameter("userId");
			sysUserService.resetPassword(Long.valueOf(userIdStr));
			map.put(Constants.SUCCESS, true);
			map.put(Constants.MESSAGE, "重置成功");
		} catch (Exception e) {
			map.put(Constants.SUCCESS, false);
			commonError(logger, e,"重置异常",map);
		}
		return map;
	}
	
}


