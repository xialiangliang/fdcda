package com.keyou.fdcda.api.model; 

import java.io.Serializable;
import java.math.BigDecimal;

import com.keyou.fdcda.api.utils.DateUtil;

public class SysGood implements Serializable {
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
     * 商品名称
     */ 	
	private String name;
    /**
     * 单价
     */ 	
	private BigDecimal price;
    /**
     * 商品分类id
     */ 	
	private Long goodCategoryId;
    /**
     * 商品描述
     */ 	
	private String description;
    /**
     * 商品总数
     */ 	
	private Long totalCount;
    /**
     * 商品剩余数量
     */ 	
	private Long remainedCount;
    /**
     * 上架时间
     */ 	
	private java.util.Date upTime;
	private String upTimeStr;
    /**
     * 下架时间
     */ 	
	private java.util.Date downTime;
	private String downTimeStr;
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
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setPrice(BigDecimal value) {
		this.price = value;
	}
	
	public BigDecimal getPrice() {
		return this.price;
	}
	
	public void setGoodCategoryId(Long value) {
		this.goodCategoryId = value;
	}
	
	public Long getGoodCategoryId() {
		return this.goodCategoryId;
	}
	
	public void setDescription(String value) {
		this.description = value;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setTotalCount(Long value) {
		this.totalCount = value;
	}
	
	public Long getTotalCount() {
		return this.totalCount;
	}
	
	public void setRemainedCount(Long value) {
		this.remainedCount = value;
	}
	
	public Long getRemainedCount() {
		return this.remainedCount;
	}
	
	public void setUpTime(java.util.Date value) {
		this.upTimeStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.upTime = value;
	}
	
	public java.util.Date getUpTime() {
		return this.upTime;
	}
	
	public String getUpTimeStr() {
		return this.upTimeStr;
	}
	
	public void setDownTime(java.util.Date value) {
		this.downTimeStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.downTime = value;
	}
	
	public java.util.Date getDownTime() {
		return this.downTime;
	}
	
	public String getDownTimeStr() {
		return this.downTimeStr;
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

