package com.keyou.fdcda.api.service;

import java.util.List;

import com.keyou.fdcda.api.model.ImageInfo;
import com.keyou.fdcda.api.service.base.BaseService;

public interface ImageInfoService extends BaseService<ImageInfo,Long>{

	/**
	 * 批量插入
	 * @param patchList
	 * @throws Exception
	 */
	public void insertBatch(List<ImageInfo> patchList) throws Exception;
}
