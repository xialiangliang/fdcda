package com.keyou.fdcda.api.model;

import java.io.Serializable;

public class CustomerBlackVip implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * 采购商rowid
     */ 	
	private java.lang.Long customerRowId;
    /**
     * 经营户rowid
     */ 	
	private java.lang.Long userRowId;
    /**
     * 0用户黑名单1系统黑名单2正常
     */ 	
	private Integer blackType;
    /**
     * 0是用户vip,1正常,2商城vip
     */ 	
	private Integer vipType;

	
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
	
	public void setBlackType(Integer value) {
		this.blackType = value;
	}
	
	public Integer getBlackType() {
		return this.blackType;
	}
	
	public void setVipType(Integer value) {
		this.vipType = value;
	}
	
	public Integer getVipType() {
		return this.vipType;
	}

	
}

