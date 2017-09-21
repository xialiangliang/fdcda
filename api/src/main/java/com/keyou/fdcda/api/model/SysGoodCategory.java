package com.keyou.fdcda.api.model; 

import java.io.Serializable;
import com.keyou.fdcda.api.utils.DateUtil;

public class SysGoodCategory implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */ 	
	private Long id;
    /**
     * 上级id
     */ 	
	private Long parentId;
    /**
     * 所有下级id
     */ 	
	private String childIdList;
    /**
     * 分类名称
     */ 	
	private String name;
    /**
     * 创建时间
     */ 	
	private java.util.Date createTime;
	private String createTimeStr;
    /**
     * 修改时间
     */ 	
	private java.util.Date modifyTime;
	private String modifyTimeStr;

	
	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setParentId(Long value) {
		this.parentId = value;
	}
	
	public Long getParentId() {
		return this.parentId;
	}
	
	public void setChildIdList(String value) {
		this.childIdList = value;
	}
	
	public String getChildIdList() {
		return this.childIdList;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTimeStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public String getCreateTimeStr() {
		return this.createTimeStr;
	}
	
	public void setModifyTime(java.util.Date value) {
		this.modifyTimeStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.modifyTime = value;
	}
	
	public java.util.Date getModifyTime() {
		return this.modifyTime;
	}
	
	public String getModifyTimeStr() {
		return this.modifyTimeStr;
	}

	
}

