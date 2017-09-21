package com.keyou.fdcda.api.model; 

import java.io.Serializable;
import com.keyou.fdcda.api.utils.DateUtil;

public class SysGoodOrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */ 	
	private Long id;
    /**
     * 订单id
     */ 	
	private Long goodOrderId;
    /**
     * 商品id
     */ 	
	private Long goodId;
    /**
     * 实际单价
     */ 	
	private Long price;
    /**
     * 商品数量
     */ 	
	private Long count;
    /**
     * 订单状态
     */ 	
	private Long state;
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
	
	public void setGoodOrderId(Long value) {
		this.goodOrderId = value;
	}
	
	public Long getGoodOrderId() {
		return this.goodOrderId;
	}
	
	public void setGoodId(Long value) {
		this.goodId = value;
	}
	
	public Long getGoodId() {
		return this.goodId;
	}
	
	public void setPrice(Long value) {
		this.price = value;
	}
	
	public Long getPrice() {
		return this.price;
	}
	
	public void setCount(Long value) {
		this.count = value;
	}
	
	public Long getCount() {
		return this.count;
	}
	
	public void setState(Long value) {
		this.state = value;
	}
	
	public Long getState() {
		return this.state;
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

