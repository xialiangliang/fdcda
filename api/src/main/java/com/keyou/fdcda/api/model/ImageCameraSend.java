package com.keyou.fdcda.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.keyou.fdcda.api.utils.DateUtil;


public class ImageCameraSend implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * 主键
     */ 	
	private BigDecimal id;
    /**
     * 图片唯id
     */ 	
	private BigDecimal imageRowId;
    /**
     * 图片url
     */ 	
	private java.lang.String imageUrl;
    /**
     * 0待处理1已处理
     */ 	
	private Integer imageStatus;
    /**
     * 用户id
     */ 	
	private BigDecimal userRowId;
    /**
     * 0识别失败(基准库里没有对应的人)，1识别成功,2未识别,3不能识别(图片原因)
     */ 	
	private Integer dealFlag;
    /**
     * 相识度
     */ 	
	private BigDecimal similarDegree;
    /**
     * 修改时间
     */ 	
	private java.util.Date modifyDate;
	private java.lang.String modifyDateStr;
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
	
	public void setImageRowId(BigDecimal value) {
		this.imageRowId = value;
	}
	
	public BigDecimal getImageRowId() {
		return this.imageRowId;
	}
	
	public void setImageUrl(java.lang.String value) {
		this.imageUrl = value;
	}
	
	public java.lang.String getImageUrl() {
		return this.imageUrl;
	}
	
	public void setImageStatus(Integer value) {
		this.imageStatus = value;
	}
	
	public Integer getImageStatus() {
		return this.imageStatus;
	}
	
	public void setUserRowId(BigDecimal value) {
		this.userRowId = value;
	}
	
	public BigDecimal getUserRowId() {
		return this.userRowId;
	}
	
	public void setDealFlag(Integer value) {
		this.dealFlag = value;
	}
	
	public Integer getDealFlag() {
		return this.dealFlag;
	}
	
	public void setSimilarDegree(BigDecimal value) {
		this.similarDegree = value;
	}
	
	public BigDecimal getSimilarDegree() {
		return this.similarDegree;
	}
	
	public void setModifyDate(java.util.Date value) {
		this.modifyDateStr =DateUtil.getDate(value, "yyyy-MM-dd");
		this.modifyDate = value;
	}
	
	public java.util.Date getModifyDate() {
		return this.modifyDate;
	}
	
	public java.lang.String getModifyDateStr() {
		return this.modifyDateStr;
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

