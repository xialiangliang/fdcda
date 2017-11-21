package com.keyou.fdcda.app.dao;

import java.math.BigDecimal;

import org.springframework.stereotype.Repository;

import com.keyou.fdcda.api.model.ImageInfo;
import com.keyou.fdcda.app.dao.base.BaseMapper;

@Repository
public interface ImageInfoMapper extends BaseMapper<ImageInfo, BigDecimal> {	

}
