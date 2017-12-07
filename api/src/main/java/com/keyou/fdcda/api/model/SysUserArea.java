package com.keyou.fdcda.api.model; 

import java.io.Serializable;
import com.keyou.fdcda.api.utils.DateUtil;

public class SysUserArea implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * 主键
     */ 	
	private Long id;
    /**
     * 区域名称
     */ 	
	private String areaName;
    /**
     * 行业
     */ 	
	private String tradeName;
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
	
	public void setAreaName(String value) {
		this.areaName = value;
	}
	
	public String getAreaName() {
		return this.areaName;
	}
	
	public void setTradeName(String value) {
		this.tradeName = value;
	}
	
	public String getTradeName() {
		return this.tradeName;
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

