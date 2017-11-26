package com.keyou.fdcda.api.constants;

public interface ImageInfoConstants {
	/**图片状态：未处理*/
	Integer IMAGE_STATUS_not_deal = 0;
	
	/**图片状态：已处理*/
	Integer IMAGE_STATUS_has_deal = 1;
	
	/**经营户上传采购商图片的根目录*/
	String  UPLOAD_IMAGE_FILE_BASE_PATH = "/mnt/facepics/customer/";
	
	/**经营户上传升级黑名单证据根目录*/
	String  UPLOAD_EVIDENCE_FILE_BASE_PATH = "/mnt/facepics/evidence/";
	 
}
