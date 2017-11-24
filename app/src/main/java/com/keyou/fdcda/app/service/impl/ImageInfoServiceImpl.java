package com.keyou.fdcda.app.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keyou.fdcda.api.model.ImageInfo;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.ImageInfoService;
import com.keyou.fdcda.app.dao.ImageInfoMapper;

@Service("imageInfoService")
public class ImageInfoServiceImpl implements ImageInfoService {

	private final static Logger logger = LoggerFactory.getLogger(ImageInfoServiceImpl.class);

	@Autowired
	private ImageInfoMapper imageInfoMapper;

	@Override
	public ImageInfo findById(Long id) {
		return null;
	}

	@Override
	public Long update(ImageInfo entity) {
		return null;
	}

	@Override
	public void deleteById(Long id) {

	}

	@Override
	public Integer save(ImageInfo entity) {
		return null;
	}

	@Override
	public Long findPageCount(Map<String, Object> map) {
		return null;
	}

	@Override
	public PageResult<ImageInfo> findPage(PaginationQuery query) {
		return null;
	}

	@Override
	public List<ImageInfo> findAllPage(Map<String, Object> map) {
		return null;
	}

	public void insertBatch(List<ImageInfo> patchList) throws Exception {
		logger.info(" begin image info insertbatch======== ");
		if (patchList != null && patchList.size() > 0) {
			int len = patchList.size();
			if (len > 1000) {
				int c = len / 1000;
				for (int i = 0; i < c; i++) {
					List<ImageInfo> subList = patchList.subList(i * 1000, (i + 1) * 1000);
					imageInfoMapper.insertList(subList);
				}
				if (c * 1000 < len) {
					List<ImageInfo> subList = patchList.subList(c * 1000, len);
					imageInfoMapper.insertList(subList);
				}
			} else {
				imageInfoMapper.insertList(patchList);
			}
		}
		
		logger.info(" end image info insertbatch======== ");
	}
}
