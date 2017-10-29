package com.keyou.fdcda.api.model; 

import java.io.Serializable;
import com.keyou.fdcda.api.utils.DateUtil;

public class ImageBaseSend implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * 主键
     */ 	
	private Long id;
    /**
     * 用户id
     */ 	
	private Long userRowId;
    /**
     * 图片url
     */ 	
	private String imageUrl;
    /**
     * 0待处理1已处理
     */ 	
	private Integer imageStatus;
    /**
     * 创建时间
     */ 	
	private java.util.Date createDate;
	private String createDateStr;

	
	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setUserRowId(Long value) {
		this.userRowId = value;
	}
	
	public Long getUserRowId() {
		return this.userRowId;
	}
	
	public void setImageUrl(String value) {
		this.imageUrl = value;
	}
	
	public String getImageUrl() {
		return this.imageUrl;
	}
	
	public void setImageStatus(Integer value) {
		this.imageStatus = value;
	}
	
	public Integer getImageStatus() {
		return this.imageStatus;
	}
	
	public void setCreateDate(java.util.Date value) {
		this.createDateStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.createDate = value;
	}
	
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	public String getCreateDateStr() {
		return this.createDateStr;
	}

	
}

