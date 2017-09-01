package com.keyou.fdcda.api.model; 

import java.io.Serializable;
import com.keyou.fdcda.api.utils.DateUtil;

public class SysUser implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */ 	
	private java.lang.Long id;
    /**
     * 用户登录名
     */ 	
	private java.lang.String loginname;
    /**
     * 登录密码，加密后
     */ 	
	private java.lang.String loginpwd;
    /**
     * 用户名
     */ 	
	private java.lang.String username;
    /**
     * 手机号
     */ 	
	private java.lang.String phone;
    /**
     * 固定电话
     */ 	
	private java.lang.String telephone;
    /**
     * 邮件地址
     */ 	
	private java.lang.String email;
    /**
     * 地址
     */ 	
	private java.lang.String address;
    /**
     * 状态 1-有效 2-无效
     */ 	
	private Integer valid;
    /**
     * 创建时间
     */ 	
	private java.util.Date createTime;
	private java.lang.String createTimeStr;
    /**
     * 修改时间
     */ 	
	private java.util.Date modifyTime;
	private java.lang.String modifyTimeStr;

	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setLoginname(java.lang.String value) {
		this.loginname = value;
	}
	
	public java.lang.String getLoginname() {
		return this.loginname;
	}
	
	public void setLoginpwd(java.lang.String value) {
		this.loginpwd = value;
	}
	
	public java.lang.String getLoginpwd() {
		return this.loginpwd;
	}
	
	public void setUsername(java.lang.String value) {
		this.username = value;
	}
	
	public java.lang.String getUsername() {
		return this.username;
	}
	
	public void setPhone(java.lang.String value) {
		this.phone = value;
	}
	
	public java.lang.String getPhone() {
		return this.phone;
	}
	
	public void setTelephone(java.lang.String value) {
		this.telephone = value;
	}
	
	public java.lang.String getTelephone() {
		return this.telephone;
	}
	
	public void setEmail(java.lang.String value) {
		this.email = value;
	}
	
	public java.lang.String getEmail() {
		return this.email;
	}
	
	public void setAddress(java.lang.String value) {
		this.address = value;
	}
	
	public java.lang.String getAddress() {
		return this.address;
	}
	
	public void setValid(Integer value) {
		this.valid = value;
	}
	
	public Integer getValid() {
		return this.valid;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTimeStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public java.lang.String getCreateTimeStr() {
		return this.createTimeStr;
	}
	
	public void setModifyTime(java.util.Date value) {
		this.modifyTimeStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.modifyTime = value;
	}
	
	public java.util.Date getModifyTime() {
		return this.modifyTime;
	}
	
	public java.lang.String getModifyTimeStr() {
		return this.modifyTimeStr;
	}
}

