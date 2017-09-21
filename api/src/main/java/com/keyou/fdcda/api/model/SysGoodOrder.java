package com.keyou.fdcda.api.model; 

import java.io.Serializable;
import com.keyou.fdcda.api.utils.DateUtil;

public class SysGoodOrder implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */ 	
	private Long id;
    /**
     * 用户id
     */ 	
	private Long userId;
    /**
     * 采购商id
     */ 	
	private Long customerId;
    /**
     * 订单状态
     */ 	
	private Long state;
    /**
     * 备注
     */ 	
	private String memo;
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
	
	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	
	public void setCustomerId(Long value) {
		this.customerId = value;
	}
	
	public Long getCustomerId() {
		return this.customerId;
	}
	
	public void setState(Long value) {
		this.state = value;
	}
	
	public Long getState() {
		return this.state;
	}
	
	public void setMemo(String value) {
		this.memo = value;
	}
	
	public String getMemo() {
		return this.memo;
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

