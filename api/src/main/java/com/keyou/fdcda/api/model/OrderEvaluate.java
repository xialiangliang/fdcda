package com.keyou.fdcda.api.model; 

import java.io.Serializable;
import java.util.List;

import com.keyou.fdcda.api.utils.DateUtil;

public class OrderEvaluate implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * 主键
     */ 	
	private Long id;
    /**
     * 订单id
     */ 	
	private Long orderRowId;
    /**
     * 评价内容
     */ 	
	private String content;
    /**
     * 评价类型：0差评1中评2好评
     */ 	
	private Integer evaType;
    /**
     * 附件，多个图片url以英文逗号,隔开 
     */ 	
	private String imagesUrl;
    /**
     * 评价日期
     */ 	
	private java.util.Date createDate;
	private String createDateStr;

	/**
	 * 评价人
	 */
	private String evaluateName;
	/**
	 * 附件，多个图片url以英文逗号,隔开 
	 */
	private List<String> imagesUrlList;

	
	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setOrderRowId(Long value) {
		this.orderRowId = value;
	}
	
	public Long getOrderRowId() {
		return this.orderRowId;
	}
	
	public void setContent(String value) {
		this.content = value;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setEvaType(Integer value) {
		this.evaType = value;
	}
	
	public Integer getEvaType() {
		return this.evaType;
	}
	
	public void setImagesUrl(String value) {
		this.imagesUrl = value;
	}
	
	public String getImagesUrl() {
		return this.imagesUrl;
	}
	
	public void setCreateDate(java.util.Date value) {
		this.createDateStr =DateUtil.getDate(value, "yyyy.MM.dd");
		this.createDate = value;
	}
	
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	public String getCreateDateStr() {
		return this.createDateStr;
	}

	public String getEvaluateName() {
		return evaluateName;
	}

	public void setEvaluateName(String evaluateName) {
		this.evaluateName = evaluateName;
	}

	public List<String> getImagesUrlList() {
		return imagesUrlList;
	}

	public void setImagesUrlList(List<String> imagesUrlList) {
		this.imagesUrlList = imagesUrlList;
	}
}

