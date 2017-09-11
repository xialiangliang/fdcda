package com.keyou.fdcda.api.model; 

import java.io.Serializable;
import java.util.List;

import com.keyou.fdcda.api.utils.DateUtil;

public class SysResource implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */ 	
	private java.lang.Long id;
    /**
     * 父资源id
     */ 	
	private java.lang.Long parentId;
    /**
     * 资源名称
     */ 	
	private java.lang.String name;
    /**
     * 资源路径
     */ 		
	private java.lang.String url;
    /**
     * 类型 1-菜单 2-按钮
     */ 	
	private Integer type;
	/**
	 * 资源路径
	 */
	private java.lang.String icon;
    /**
     * 排序
     */ 	
	private java.lang.Integer sort;
    /**
     * 备注
     */ 	
	private java.lang.String memo;
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
	
	private List<SysResource> subResource;

	/**
	 * 是否有该权限
	 */
	private Long auth;

	/**
	 * 拥有该资源权限的角色id
	 */
	private String roleIdsStr;

	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setParentId(java.lang.Long value) {
		this.parentId = value;
	}
	
	public java.lang.Long getParentId() {
		return this.parentId;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setUrl(java.lang.String value) {
		this.url = value;
	}
	
	public java.lang.String getUrl() {
		return this.url;
	}
	
	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setSort(java.lang.Integer value) {
		this.sort = value;
	}
	
	public java.lang.Integer getSort() {
		return this.sort;
	}
	
	public void setMemo(java.lang.String value) {
		this.memo = value;
	}
	
	public java.lang.String getMemo() {
		return this.memo;
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

    public List<SysResource> getSubResource() {
        return subResource;
    }

    public void setSubResource(List<SysResource> subResource) {
        this.subResource = subResource;
    }

	public Long getAuth() {
		return auth;
	}

	public void setAuth(Long auth) {
		this.auth = auth;
	}

	public String getRoleIdsStr() {
		return roleIdsStr;
	}

	public void setRoleIdsStr(String roleIdsStr) {
		this.roleIdsStr = roleIdsStr;
	}
}

