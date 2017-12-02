package com.keyou.fdcda.api.model; 

import java.io.Serializable;
import java.util.List;

import com.keyou.fdcda.api.utils.DateUtil;

public class SysBlacklistApply implements Serializable {
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
     * 采购商id
     */ 	
	private Long customerRowId;
    /**
     * 备注
     */ 	
	private String reason;
    /**
     * 多个文件以;隔开
     */ 	
	private String fileUrl;
    /**
     * 0新建1审核通过2驳回
     */ 	
	private Integer status;
    /**
     * 创建时间
     */ 	
	private java.util.Date createDate;
	private String createDateStr;
    /**
     * 修改时间
     */ 	
	private java.util.Date modifyDate;
	private String modifyDateStr;

	List<String> fileUrlList;

	/**
	 * 提交人
	 */
	String userName;
	/**
	 * 采购商姓名
	 */
	String customerName;
	/**
	 * 采购商手机号
	 */
	String customerPhone;
	
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
	
	public void setCustomerRowId(Long value) {
		this.customerRowId = value;
	}
	
	public Long getCustomerRowId() {
		return this.customerRowId;
	}
	
	public void setReason(String value) {
		this.reason = value;
	}
	
	public String getReason() {
		return this.reason;
	}
	
	public void setFileUrl(String value) {
		this.fileUrl = value;
	}
	
	public String getFileUrl() {
		return this.fileUrl;
	}
	
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
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
	
	public void setModifyDate(java.util.Date value) {
		this.modifyDateStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.modifyDate = value;
	}
	
	public java.util.Date getModifyDate() {
		return this.modifyDate;
	}
	
	public String getModifyDateStr() {
		return this.modifyDateStr;
	}

	public List<String> getFileUrlList() {
		return fileUrlList;
	}

	public void setFileUrlList(List<String> fileUrlList) {
		this.fileUrlList = fileUrlList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
}

