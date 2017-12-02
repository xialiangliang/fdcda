package com.keyou.fdcda.app.dao;

import org.springframework.stereotype.Repository;

import com.keyou.fdcda.api.model.ImageInfo;
import com.keyou.fdcda.app.dao.base.BaseMapper;

@Repository
public interface ImageInfoMapper extends BaseMapper<ImageInfo, Long> {	

	/**
	 * 调用存储过程
	 */
	void selectCallPro();
}
