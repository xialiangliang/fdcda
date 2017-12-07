package com.keyou.fdcda.home.controller;

import java.security.PrivateKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.keyou.fdcda.api.constants.Constants;
import com.keyou.fdcda.api.constants.ImageInfoConstants;
import com.keyou.fdcda.api.constants.RedisConstants;
import com.keyou.fdcda.api.model.SysUser;
import com.keyou.fdcda.api.model.VisitRecordInfo;
import com.keyou.fdcda.api.service.RedisService;
import com.keyou.fdcda.api.service.SysUserService;
import com.keyou.fdcda.api.service.VisitRecordInfoService;
import com.keyou.fdcda.api.utils.EncodeUtil;
import com.keyou.fdcda.api.utils.RandomUtil;
import com.keyou.fdcda.api.utils.StringUtil;
import com.keyou.fdcda.api.utils.config.UrlConfig;
import com.keyou.fdcda.home.controller.base.BaseController;

/**
 * Created by zzq on 2017-07-08.
 */
@Controller
@RequestMapping("/")
public class IndexController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private RedisService redisService;
    @Autowired
    private UrlConfig urlConfig;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
	private VisitRecordInfoService visitRecordInfoService;

    @RequestMapping
    public String idx(Model model) {
        return "redirect:/index";
    }
    
    private void dealUrl(List<VisitRecordInfo> list){
		if (list != null && !list.isEmpty()) {
			for (VisitRecordInfo info : list) {
				Integer type = info.getVisitType();
				if (type != null&&StringUtil.isNotBlank(info.getImageUrl())) {
					String url = info.getImageUrl();
					info.setImageUrl(urlConfig.getImgPath() + "/"+url.substring(url.indexOf("deal")));
					 
				}
			}
		}
	}

    @RequestMapping("/index")
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
		
		return "/page/index";
	}
    
    @RequestMapping("modifyPassword")
    public String modifyPassword(Model model, HttpServletRequest request) {
        String salt = RandomUtil.produceString(64);
        model.addAttribute("salt", salt);
        SysUser sysUser = (SysUser) request.getSession().getAttribute(Constants.SESSION_USER);
        redisService.set(RedisConstants.MODIFY_PASSWORD_SALT + sysUser.getId(), salt, 10 * 1000);
        String token = RandomUtil.produceString(32);
        request.getSession().setAttribute(Constants.SESSION_LOGIN_TOKEN, token);
        model.addAttribute("token", token);
        return "/page/modifyPassword";
    }


    @RequestMapping(value="modifyPassword/confirm", method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modifyPasswordConfirm(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.SUCCESS, false);
        String originPwd = request.getParameter("originPwd");
        String newPwd = request.getParameter("newPwd");
        String confirmNewPwd = request.getParameter("confirmNewPwd");
        SysUser sysUser = (SysUser) request.getSession().getAttribute(Constants.SESSION_USER);
        sysUser = sysUserService.findById(sysUser.getId());
        String token = (String) request.getSession().getAttribute(Constants.SESSION_LOGIN_TOKEN);
        String dbPwd = EncodeUtil.hash(sysUser.getLoginpwd().split("\\" + Constants.PASSWORD_SALT_SPLIT)[0] + token, Constants.HASH_ENCODE);
        if (!dbPwd.equals(originPwd)) {
            map.put(Constants.MESSAGE, "原密码不正确");
            return map;
        }
        PrivateKey privateKey = (PrivateKey) request.getSession().getAttribute("privateKey");
        byte[] decryptedBytes1;
        byte[] decryptedBytes2;
        try {
            decryptedBytes1 = EncodeUtil.rsaDecrypt(Base64.getDecoder().decode(newPwd), privateKey);
            decryptedBytes2 = EncodeUtil.rsaDecrypt(Base64.getDecoder().decode(confirmNewPwd), privateKey);
        } catch (Exception e) {
            map.put(Constants.MESSAGE, "密码解码错误");
            return map;
        }
        newPwd = new String(decryptedBytes1);
        if (!newPwd.equals(new String(decryptedBytes2))) {
            map.put(Constants.MESSAGE, "新密码不一致");
            return map; 
        }
        sysUser.setLoginpwd(new String(decryptedBytes1));
        SysUser vo = new SysUser();
        vo.setId(sysUser.getId());
        String salt = sysUserService.generatSalt().getData();
        vo.setLoginpwd(EncodeUtil.hash(newPwd + salt, Constants.HASH_ENCODE) + Constants.PASSWORD_SALT_SPLIT + salt);
        sysUserService.update(vo);
        request.getSession().removeAttribute(Constants.SESSION_LOGIN_TOKEN);
        map.put(Constants.SUCCESS, true);
        map.put(Constants.MESSAGE, "修改成功");
        return map;
    }

    @RequestMapping("nopermit")
    public String nopermit(Model model) {
        return "/nopermit"; 
    }

    @RequestMapping("systemManage")
    public String systemManage(Model model) {
        return "/nopermit";
    }

    @RequestMapping("analysis")
    public String analysis(Model model) {
        return "/nopermit";
    }

    @RequestMapping("viewRecord")
    public String viewRecord(Model model) {
        return "/nopermit";
    }

    @RequestMapping("customer")
    public String customer(Model model) {
        return "/nopermit";
    }

    @RequestMapping("customerManage")
    public String customerManage(Model model) {
        return "/nopermit";
    }

    @RequestMapping("vipManage")
    public String vipManage(Model model) {
        return "/nopermit";
    }

    @RequestMapping("visit")
    public String visit(Model model) {
        return "/nopermit";
    }
}
