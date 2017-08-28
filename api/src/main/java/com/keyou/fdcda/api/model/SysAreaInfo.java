package com.keyou.fdcda.api.model; 

import java.io.Serializable;
import com.keyou.fdcda.api.utils.DateUtil;

public class SysAreaInfo implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id主键
     */ 	
	private java.lang.Long id;
    /**
     * 区域code
     */ 	
	private java.lang.String code;
    /**
     * 区域名
     */ 	
	private java.lang.String areaname;
    /**
     * 排序
     */ 	
	private java.lang.Long sort;
    /**
     * 是否有效1有效2无效
     */ 	
	private Integer vaild;
    /**
     * 父区域code
     */ 	
	private java.lang.String parentcode;

	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setCode(java.lang.String value) {
		this.code = value;
	}
	
	public java.lang.String getCode() {
		return this.code;
	}
	
	public void setAreaname(java.lang.String value) {
		this.areaname = value;
	}
	
	public java.lang.String getAreaname() {
		return this.areaname;
	}
	
	public void setSort(java.lang.Long value) {
		this.sort = value;
	}
	
	public java.lang.Long getSort() {
		return this.sort;
	}
	
	public void setVaild(Integer value) {
		this.vaild = value;
	}
	
	public Integer getVaild() {
		return this.vaild;
	}
	
	public void setParentcode(java.lang.String value) {
		this.parentcode = value;
	}
	
	public java.lang.String getParentcode() {
		return this.parentcode;
	}

	
}

