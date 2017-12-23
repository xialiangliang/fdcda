package com.keyou.fdcda.api.model; 

import java.io.Serializable;
import com.keyou.fdcda.api.utils.DateUtil;

public class SysDevice implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */ 	
	private Long id;
    /**
     * 门店id
     */ 	
	private Long outletsId;
    /**
     * 设备序列号
     */ 	
	private String seqno;
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
	
	/**
	 * 门店名
	 */
	private String outletsName;

	
	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setOutletsId(Long value) {
		this.outletsId = value;
	}
	
	public Long getOutletsId() {
		return this.outletsId;
	}
	
	public void setSeqno(String value) {
		this.seqno = value;
	}
	
	public String getSeqno() {
		return this.seqno;
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

	public String getOutletsName() {
		return outletsName;
	}

	public void setOutletsName(String outletsName) {
		this.outletsName = outletsName;
	}
}

