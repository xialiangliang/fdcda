package com.keyou.fdcda.api.model; 

import java.io.Serializable;
import java.math.BigDecimal;

import com.keyou.fdcda.api.utils.DateUtil;

public class OrderInfo implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * 主键
     */ 	
	private Long id;
    /**
     * 经营户id
     */ 	
	private Long userRowId;
    /**
     * 采购商id
     */ 	
	private Long customerRowId;
    /**
     * 交易内容
     */ 	
	private String orderContent;
    /**
     * 交易金额
     */ 	
	private BigDecimal orderAmt;
    /**
     * 付款方式：1现金2银行卡3支付宝4微信
     */ 	
	private Integer payType;
    /**
     * 交货日期
     */ 	
	private java.util.Date goodDate;
	private String goodDateStr;
    /**
     * 收货地址
     */ 	
	private String toaddress;
    /**
     * 备注
     */ 	
	private String remark;
    /**
     * 创建日期
     */ 	
	private java.util.Date createDate;
	private String createDateStr;

	/**
	 * 订单状态 1-未付款 2-已付款 3-待收尾款
	 */
	private Integer status;
	
	private String customerName;
	private String customerPhone;

	
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
	
	public void setOrderContent(String value) {
		this.orderContent = value;
	}
	
	public String getOrderContent() {
		return this.orderContent;
	}
	
	public void setOrderAmt(BigDecimal value) {
		this.orderAmt = value;
	}
	
	public BigDecimal getOrderAmt() {
		return this.orderAmt;
	}
	
	public void setPayType(Integer value) {
		this.payType = value;
	}
	
	public Integer getPayType() {
		return this.payType;
	}
	
	public void setGoodDate(java.util.Date value) {
		this.goodDateStr =DateUtil.getDate(value, "yyyy-MM-dd");
		this.goodDate = value;
	}
	
	public java.util.Date getGoodDate() {
		return this.goodDate;
	}

	public void setGoodDateStr(String goodDateStr) {
		this.goodDateStr = goodDateStr;
	}

	public String getGoodDateStr() {
		return this.goodDateStr;
	}
	
	public void setToaddress(String value) {
		this.toaddress = value;
	}
	
	public String getToaddress() {
		return this.toaddress;
	}
	
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}

