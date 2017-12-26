package com.keyou.fdcda.home.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
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
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.CustomerInfoService;
import com.keyou.fdcda.api.service.ImageInfoService;
import com.keyou.fdcda.api.service.VisitRecordInfoService;
import com.keyou.fdcda.api.utils.DateUtil;
import com.keyou.fdcda.api.utils.StringUtil;
import com.keyou.fdcda.api.utils.config.UrlConfig;
import com.keyou.fdcda.home.controller.base.BaseController;
import com.keyou.fdcda.home.face.faceManager;
import com.keyou.fdcda.home.face.imageUpLoader;
import com.keyou.fdcda.home.face.resourceManager;


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
		
		model.addAttribute("begin", begin);
		model.addAttribute("end", end);
		 
		return "/page/visitinfo/list";
	}
	
	@RequestMapping(value = "/visitRecordInfo/query", method = RequestMethod.GET)
	public String queryList(PaginationQuery query, Model model, HttpServletRequest request,String nameStr, String phoneStr) throws Exception {
		SysUser sysUser = getUser(request);
		if (sysUser == null) {
			return "redirect:/login";
		}
		query.addQueryData("userRowId", sysUser.getId()+""); 
		query.addQueryData("nameStr", nameStr);
		query.addQueryData("phoneStr", phoneStr);
		model.addAttribute("query", query.getQueryData());
		return "/page/visitinfo/listquery";
	}

	@RequestMapping("/visitRecordInfo/listJson")
	@ResponseBody
	public Map<String, Object> listJson(PaginationQuery query,Model model, HttpServletRequest request, String nameStr, String phoneStr, Integer page, Integer limit) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		this.formatPageQuery(query, page, limit);
		query.addQueryData("userRowId", getUser(request).getId().toString());
		query.addQueryData("nameStr", nameStr);
		query.addQueryData("phoneStr", phoneStr);
		
		PageResult<VisitRecordInfo> pageList = visitRecordInfoService.findPage(query);
		List<VisitRecordInfo> list = pageList.getRows();
		dealUrl(list);
		map.put("data", list);
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", pageList.getRowCount());
		map.put("query", query.getQueryData());
		return map;
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
	
	@RequestMapping(value = "/visitRecordInfo/toyitusoutu")
    public String toyitusoutu( Model model,PaginationQuery query) {
        return "/page/visitinfo/yituquery";
    }
	
	@RequestMapping(value = "/visitRecordInfo/yitusoutu" , method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> findImages(@RequestParam(value = "file", required = false) MultipartFile file, Model model,PaginationQuery query) {
		Map<String, Object> map = new HashMap<>();
		if (file !=null) {
        	JSONObject responseJson = null;
    		JSONObject requestJson = null;
    		String sessionId = "";
    		String faceImageId = "";
    		// 登陆
    		resourceManager testResource = new resourceManager(ImageInfoConstants.YITU_HOST);
    		try {
    			//String loginData = getFileStr("接口requestData\\登陆.txt");
    			testResource.setRequestData(ImageInfoConstants.YITU_API_LOGIN);// 写入文件的json格式data
    			testResource.userLogin();// 登陆
    			sessionId = testResource.sessionId;// 保存sessionId
    			responseJson = new JSONObject(testResource.response);// 保存返回结果
    		} catch (Exception e) {
    			System.out.println("登陆接口出现问题：" + e);
    		}
    		if (testResource.responseCode == 200) {
    			System.out.println("登陆接口可用。");
    			testResource.responseCode = 0;
    		} else {
    		}
    		// 导图同步
    		imageUpLoader testImageUploader = new imageUpLoader(ImageInfoConstants.YITU_HOST);
    		try {
    			testImageUploader.setSessionId(sessionId);
    			requestJson = new JSONObject(ImageInfoConstants.YITU_API_UPLOAD_IMAGE);
    			requestJson.put("picture_image_content_base64", Base64.getEncoder().encodeToString(file.getBytes()));
    			// requestJson.put("repository_id", repositoryId1);
    			testImageUploader.setRequestData(requestJson.toString());
    			testImageUploader.upLoadSyn();
    			responseJson = new JSONObject(testImageUploader.response);
    			if (testImageUploader.responseCode == 200) {
    				faceImageId = responseJson.getJSONArray("results").getJSONObject(0).getString("face_image_id");
    				//String pictureUri = responseJson.getString("picture_uri");
    				System.out.println("同步导图接口可用。");
    				testImageUploader.responseCode = 0;
    			} else {
    			}
    		} catch (Exception e) {
    			System.out.println("同步导图接口出现问题：" + e);
    		}
    		// 人脸检索
    		faceManager testFaceManager = new faceManager(ImageInfoConstants.YITU_HOST);
    		try {
    			testFaceManager.setSessionId(sessionId);
    			requestJson = new JSONObject(ImageInfoConstants.YITU_API_SEARCH);
    			JSONObject faceRetrieval = new JSONObject(requestJson.getJSONObject("retrieval").toString());
    			JSONArray repositoryIds = new JSONArray(
    					requestJson.getJSONObject("retrieval").getJSONArray("repository_id").toString());
    			faceRetrieval.put("face_image_id", faceImageId);
    			// repositoryIds.put(1);
    			faceRetrieval.put("repository_ids", repositoryIds);
    			requestJson.put("retrieval", faceRetrieval);
    			requestJson.put("extra_fields", new String[]{"image_row_id"} );
    			System.out.println(requestJson.toString());
    			testFaceManager.setRequestData(requestJson.toString());
    			testFaceManager.showFaceRetrieval();
    			//responseJson = new JSONObject(testFaceManager.response);
    		} catch (Exception e) {
    			System.out.println("人脸检索接口出现问题：" + e);
    		}
    		if (testFaceManager.responseCode == 200) {
    			System.out.println("人脸检索接口可用。");
    			//testFaceManager.responseCode = 0;
    			String ids = getIdFromResult(testFaceManager.response);
    			//执行查询
    			query.addQueryData("idstr", ids);
    			//model.addAttribute("query", query.getQueryData());
    			map.put("query", query.getQueryData());
    		}
		}
        return map;
    }
	
	@RequestMapping("/visitRecordInfo/listJsonYItu")
	@ResponseBody
	public Map<String, Object> listJsonYItu(PaginationQuery query,Model model, HttpServletRequest request, String idstr, Integer page, Integer limit) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		this.formatPageQuery(query, page, limit);
		List<Long> idList = new ArrayList<>();
		if (StringUtil.isNotEmpty(idstr)) {
			if (idstr.endsWith(",")) {
				idstr = idstr.substring(0, idstr.length() - 1);
			}
			String [] idsarry = idstr.split(",");
			for (int i = 0; i < idsarry.length; i++) {
				try {
					idList.add(Long.parseLong(idsarry[i]));
				} catch (Exception e) {
					log.error(e);
					continue;
				}
			}
 		}
		query.addQueryData("ids", idList);
		
		PageResult<VisitRecordInfo> pageList = visitRecordInfoService.findPage(query);
		List<VisitRecordInfo> list = pageList.getRows();
		dealUrl(list);
		map.put("data", list);
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", pageList.getRowCount());
		map.put("query", query.getQueryData());
		return map;
	}
	
	private String getIdFromResult(String result ){
		StringBuffer ids = new StringBuffer();
		try {
			
			JSONObject resultjson = new JSONObject("{\"message\":\"OK\",\"results\":[{\"annotation\":0,\"born_year\":0,\"face_image_id\":\"1407374883553280@DEFAULT\",\"face_image_id_str\":\"1407374883553280@DEFAULT\",\"face_image_uri\":\"normal://repository-builder/20171226/pVTcHSxn0AFoAgzsNUj8PA==@1@DEFAULT\",\"face_rect\":{\"h\":85,\"w\":85,\"x\":38,\"y\":30},\"gender\":0,\"image_row_id\":1245,\"is_writable\":true,\"name\":\"\",\"nation\":0,\"permission_map\":{\"0\":2,\"1\":2,\"101\":2,\"102\":2,\"400\":2,\"452\":2,\"501\":2,\"502\":2,\"503\":2,\"504\":2,\"505\":2,\"553\":2,\"554\":2,\"601\":2,\"602\":2,\"603\":2,\"604\":2,\"605\":2},\"person_id\":\"\",\"picture_uri\":\"normal://repository-builder/20171226/EeHAWCKmB9iePfMo1RsEJQ==@1@DEFAULT\",\"repository_id\":\"5@DEFAULT\",\"similarity\":98.03211583110513,\"timestamp\":1514246123},{\"annotation\":0,\"born_year\":0,\"face_image_id\":\"1407374883553281@DEFAULT\",\"face_image_id_str\":\"1407374883553281@DEFAULT\",\"face_image_uri\":\"normal://repository-builder/20171226/pVTcHSxn0AFoAgzsNUj8PA==@1@DEFAULT\",\"face_rect\":{\"h\":85,\"w\":85,\"x\":38,\"y\":30},\"gender\":0,\"image_row_id\":1246,\"is_writable\":true,\"name\":\"\",\"nation\":0,\"permission_map\":{\"0\":2,\"1\":2,\"101\":2,\"102\":2,\"400\":2,\"452\":2,\"501\":2,\"502\":2,\"503\":2,\"504\":2,\"505\":2,\"553\":2,\"554\":2,\"601\":2,\"602\":2,\"603\":2,\"604\":2,\"605\":2},\"person_id\":\"\",\"picture_uri\":\"normal://repository-builder/20171226/EeHAWCKmB9iePfMo1RsEJQ==@1@DEFAULT\",\"repository_id\":\"5@DEFAULT\",\"similarity\":98.03211583110513,\"timestamp\":1514246125}],\"retrieval_query_id\":\"805@DEFAULT\",\"rtn\":0,\"total\":2}");
		
			JSONArray repositoryIds =  resultjson. getJSONArray("results");
			for(int i = 0 ;i<repositoryIds.length();i++){
				String id =    repositoryIds.getJSONObject(i) .getString("image_row_id") ;
				ids.append(id).append(",");
			}
		} catch (Exception e) {
			log.error("搜图时error：" ,e);
		}
		
		return ids.toString();
	}
	
	public static void main(String[] args) {
		try {
			
			JSONObject resultjson = new JSONObject("{\"message\":\"OK\",\"results\":[{\"annotation\":0,\"born_year\":0,\"face_image_id\":\"1407374883553280@DEFAULT\",\"face_image_id_str\":\"1407374883553280@DEFAULT\",\"face_image_uri\":\"normal://repository-builder/20171226/pVTcHSxn0AFoAgzsNUj8PA==@1@DEFAULT\",\"face_rect\":{\"h\":85,\"w\":85,\"x\":38,\"y\":30},\"gender\":0,\"image_row_id\":1245,\"is_writable\":true,\"name\":\"\",\"nation\":0,\"permission_map\":{\"0\":2,\"1\":2,\"101\":2,\"102\":2,\"400\":2,\"452\":2,\"501\":2,\"502\":2,\"503\":2,\"504\":2,\"505\":2,\"553\":2,\"554\":2,\"601\":2,\"602\":2,\"603\":2,\"604\":2,\"605\":2},\"person_id\":\"\",\"picture_uri\":\"normal://repository-builder/20171226/EeHAWCKmB9iePfMo1RsEJQ==@1@DEFAULT\",\"repository_id\":\"5@DEFAULT\",\"similarity\":98.03211583110513,\"timestamp\":1514246123},{\"annotation\":0,\"born_year\":0,\"face_image_id\":\"1407374883553281@DEFAULT\",\"face_image_id_str\":\"1407374883553281@DEFAULT\",\"face_image_uri\":\"normal://repository-builder/20171226/pVTcHSxn0AFoAgzsNUj8PA==@1@DEFAULT\",\"face_rect\":{\"h\":85,\"w\":85,\"x\":38,\"y\":30},\"gender\":0,\"image_row_id\":1246,\"is_writable\":true,\"name\":\"\",\"nation\":0,\"permission_map\":{\"0\":2,\"1\":2,\"101\":2,\"102\":2,\"400\":2,\"452\":2,\"501\":2,\"502\":2,\"503\":2,\"504\":2,\"505\":2,\"553\":2,\"554\":2,\"601\":2,\"602\":2,\"603\":2,\"604\":2,\"605\":2},\"person_id\":\"\",\"picture_uri\":\"normal://repository-builder/20171226/EeHAWCKmB9iePfMo1RsEJQ==@1@DEFAULT\",\"repository_id\":\"5@DEFAULT\",\"similarity\":98.03211583110513,\"timestamp\":1514246125}],\"retrieval_query_id\":\"805@DEFAULT\",\"rtn\":0,\"total\":2}");
		
			JSONArray repositoryIds =  resultjson. getJSONArray("results");
			for(int i = 0 ;i<repositoryIds.length();i++){
				System.out.println(repositoryIds.get(i).toString());
				String id =    repositoryIds.getJSONObject(i) .getString("image_row_id") ;
				System.out.println(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
