package com.keyou.fdcda.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.keyou.fdcda.api.utils.DateUtil;


public class VisitRecordInfo implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * 主键
     */ 	
	private java.lang.Long visitRowId;
    /**
     * image_info主键
     */ 	
	private java.lang.Long imageRowId;
    /**
     * 设备编号
     */ 	
	private java.lang.String cameraCode;
    /**
     * 采购商表主id
     */ 	
	private java.lang.Long customerRowId;
    /**
     * 经营户sys_user主键
     */ 	
	private java.lang.Long userRowId;
    /**
     * 采购商姓名
     */ 	
	private java.lang.String name;
    /**
     * 手机号码
     */ 	
	private java.lang.String phone;
    /**
     * 相识度
     */ 	
	private BigDecimal similarDegree;
    /**
     * 创建日期
     */ 	
	private java.util.Date createDate;
	private java.lang.String createDateStr;
    /**
     * 0未识别出来的普通访客1已识别出来的普通访客2经营户的会员3本店设置的黑名单4系统黑名单
     */ 	
	private Integer visitType;

	private String visitYear;
	
	private String visitMonth;
	
	private java.util.Date visitDay;
	
	private Integer visitCount;
	 /**
     * 图片url
     */ 	
	private java.lang.String imageUrl;
	
	public void setVisitRowId(java.lang.Long value) {
		this.visitRowId = value;
	}
	
	public java.lang.Long getVisitRowId() {
		return this.visitRowId;
	}
	
	public void setImageRowId(java.lang.Long value) {
		this.imageRowId = value;
	}
	
	public java.lang.Long getImageRowId() {
		return this.imageRowId;
	}
	
	public void setCameraCode(java.lang.String value) {
		this.cameraCode = value;
	}
	
	public java.lang.String getCameraCode() {
		return this.cameraCode;
	}
	
	public void setCustomerRowId(java.lang.Long value) {
		this.customerRowId = value;
	}
	
	public java.lang.Long getCustomerRowId() {
		return this.customerRowId;
	}
	
	public void setUserRowId(java.lang.Long value) {
		this.userRowId = value;
	}
	
	public java.lang.Long getUserRowId() {
		return this.userRowId;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setPhone(java.lang.String value) {
		this.phone = value;
	}
	
	public java.lang.String getPhone() {
		return this.phone;
	}
	
	public void setSimilarDegree(BigDecimal value) {
		this.similarDegree = value;
	}
	
	public BigDecimal getSimilarDegree() {
		return this.similarDegree;
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
	
	public void setVisitType(Integer value) {
		this.visitType = value;
	}
	
	public Integer getVisitType() {
		return this.visitType;
	}

	public java.lang.String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(java.lang.String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getVisitYear() {
		return visitYear;
	}

	public void setVisitYear(String visitYear) {
		this.visitYear = visitYear;
	}

	public String getVisitMonth() {
		return visitMonth;
	}

	public void setVisitMonth(String visitMonth) {
		this.visitMonth = visitMonth;
	}

	public Integer getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(Integer visitCount) {
		this.visitCount = visitCount;
	}

	public java.util.Date getVisitDay() {
		return visitDay;
	}

	public void setVisitDay(java.util.Date visitDay) {
		this.visitDay = visitDay;
	}

	
}

