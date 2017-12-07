package com.keyou.fdcda.api.constants;

public interface ImageInfoConstants {
	
//	/**Nginx 静态资源目录**/
//	String STATIC_IMAGE_SERVER_URL = "http://60.191.246.29:8880/"; // 在UrlConfig中配置
	
	/**图片状态：未处理*/
	Integer IMAGE_STATUS_not_deal = 0;
	
	/**图片状态：已处理*/
	Integer IMAGE_STATUS_has_deal = 1;
	
	/**经营户上传采购商图片的根目录*/
	String  UPLOAD_IMAGE_FILE_BASE_PATH = "/mnt/facepics/customer/";
	
	/**经营户上传升级黑名单证据根目录*/
	String  UPLOAD_EVIDENCE_FILE_BASE_PATH = "/mnt/facepics/evidence/";
	
	/**图片根目录*/
	String rootPath = "/mnt/facepics/";
	
	/**未处理图片根目录*/
	String originalPath = "original";
	
	/**已处理图片根目录*/
	String dealPath = "deal";
	
	/**0未识别出来的普通访客*/
	int VISIT_TYPE_0 = 0;
	
	/**1已识别出来的普通访客*/
	int VISIT_TYPE_1 = 1;
	
	/**2经营户的会员*/
	int VISIT_TYPE_2 = 2;
	
	/**3本店设置的黑名单**/
	int VISIT_TYPE_3 = 3;
	
	/**4系统黑名单*/
	int VISIT_TYPE_4 = 4;
	
	/**5系统vip*/
	int VISIT_TYPE_5 = 5;
	
	/**6可疑人员*/
	int VISIT_TYPE_6 = 5;
	
	
	
	
	
	 
}
