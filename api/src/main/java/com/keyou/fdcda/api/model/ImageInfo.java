package com.keyou.fdcda.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.keyou.fdcda.api.utils.DateUtil;

public class ImageInfo implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * 主键
     */ 	
	private BigDecimal id;
    /**
     * 图片url
     */ 	
	private java.lang.String imageUrl;
    /**
     * 设备唯一编码
     */ 	
	private java.lang.String cameraCode;
    /**
     * 0未处理1已处理
     */ 	
	private Integer dealFlag;
    /**
     * 创建时间
     */ 	
	private java.util.Date createDate;
	private java.lang.String createDateStr;

	
	public void setId(BigDecimal value) {
		this.id = value;
	}
	
	public BigDecimal getId() {
		return this.id;
	}
	
	public void setImageUrl(java.lang.String value) {
		this.imageUrl = value;
	}
	
	public java.lang.String getImageUrl() {
		return this.imageUrl;
	}
	
	public void setCameraCode(java.lang.String value) {
		this.cameraCode = value;
	}
	
	public java.lang.String getCameraCode() {
		return this.cameraCode;
	}
	
	public void setDealFlag(Integer value) {
		this.dealFlag = value;
	}
	
	public Integer getDealFlag() {
		return this.dealFlag;
	}
	
	public void setCreateDate(java.util.Date value) {
		this.createDateStr =DateUtil.getDate(value, "yyyy-MM-dd");
		this.createDate = value;
	}
	
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	public java.lang.String getCreateDateStr() {
		return this.createDateStr;
	}

	
}

