package com.keyou.fdcda.api.model; 

import java.io.Serializable;
import com.keyou.fdcda.api.utils.DateUtil;

public class CustomerInfo implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * 主键d
     */ 	
	private BigDecimal id;
    /**
     * 采购商姓名
     */ 	
	private String name;
    /**
     * 手机号
     */ 	
	private String phone;
    /**
     * 性别：0男1女
     */ 	
	private Integer gender;
    /**
     * 国籍：1中国
     */ 	
	private Long nationality;
    /**
     * 省份
     */ 	
	private Long province;
    /**
     * 地市
     */ 	
	private Long city;
    /**
     * 单位
     */ 	
	private BigDecimal companyid;
    /**
     * 联系地址
     */ 	
	private String address;
    /**
     * 图像url
     */ 	
	private String imageUrl;
    /**
     * 是否有效：0无效1有效
     */ 	
	private Integer validFlag;
    /**
     * 记录创建时间
     */ 	
	private java.util.Date createTime;
	private String createTimeStr;
    /**
     * 联系qq
     */ 	
	private String qq;
    /**
     * 微信号
     */ 	
	private String weixin;
    /**
     * 记录修改时间
     */ 	
	private java.util.Date modifyTime;
	private String modifyTimeStr;
    /**
     * 经营户用户id(系统黑名单时为空)
     */ 	
	private BigDecimal userRowId;
    /**
     * 0不是1用户黑名单2系统黑名单
     */ 	
	private Integer isBlack;
    /**
     * 0不是1是
     */ 	
	private Integer isVip;
    /**
     * 0信息录入1etl
     */ 	
	private Integer source;

	
	public void setId(BigDecimal value) {
		this.id = value;
	}
	
	public BigDecimal getId() {
		return this.id;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setPhone(String value) {
		this.phone = value;
	}
	
	public String getPhone() {
		return this.phone;
	}
	
	public void setGender(Integer value) {
		this.gender = value;
	}
	
	public Integer getGender() {
		return this.gender;
	}
	
	public void setNationality(Long value) {
		this.nationality = value;
	}
	
	public Long getNationality() {
		return this.nationality;
	}
	
	public void setProvince(Long value) {
		this.province = value;
	}
	
	public Long getProvince() {
		return this.province;
	}
	
	public void setCity(Long value) {
		this.city = value;
	}
	
	public Long getCity() {
		return this.city;
	}
	
	public void setCompanyid(BigDecimal value) {
		this.companyid = value;
	}
	
	public BigDecimal getCompanyid() {
		return this.companyid;
	}
	
	public void setAddress(String value) {
		this.address = value;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setImageUrl(String value) {
		this.imageUrl = value;
	}
	
	public String getImageUrl() {
		return this.imageUrl;
	}
	
	public void setValidFlag(Integer value) {
		this.validFlag = value;
	}
	
	public Integer getValidFlag() {
		return this.validFlag;
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
	
	public void setQq(String value) {
		this.qq = value;
	}
	
	public String getQq() {
		return this.qq;
	}
	
	public void setWeixin(String value) {
		this.weixin = value;
	}
	
	public String getWeixin() {
		return this.weixin;
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
	
	public void setUserRowId(BigDecimal value) {
		this.userRowId = value;
	}
	
	public BigDecimal getUserRowId() {
		return this.userRowId;
	}
	
	public void setIsBlack(Integer value) {
		this.isBlack = value;
	}
	
	public Integer getIsBlack() {
		return this.isBlack;
	}
	
	public void setIsVip(Integer value) {
		this.isVip = value;
	}
	
	public Integer getIsVip() {
		return this.isVip;
	}
	
	public void setSource(Integer value) {
		this.source = value;
	}
	
	public Integer getSource() {
		return this.source;
	}

	
}

