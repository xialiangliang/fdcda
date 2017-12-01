package com.keyou.fdcda.api.model; 

import java.io.Serializable;
import com.keyou.fdcda.api.utils.DateUtil;

public class BlacklistDetails implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * 与cunstomer_info id字段关联
     */ 	
	private Long customerRowId;
    /**
     * 姓名
     */ 	
	private String name;
    /**
     * 口音
     */ 	
	private String accent;
    /**
     * 性别
     */ 	
	private String sex;
    /**
     * 生日
     */ 	
	private String birthday;
    /**
     * 身高
     */ 	
	private Long height;
    /**
     * 曾用姓名
     */ 	
	private String exname;
    /**
     * 体型
     */ 	
	private String body;
    /**
     * 脸型
     */ 	
	private String face;
    /**
     * 身体标记
     */ 	
	private String bodymark;
    /**
     * 身份证或者护照编号
     */ 	
	private String idp;
    /**
     * 督捕级别
     */ 	
	private String lv;
    /**
     * 发布日期
     */ 	
	private String publishtime;
    /**
     * 国籍，户籍住址
     */ 	
	private String address;
    /**
     * 举报电话
     */ 	
	private String reporttel;
    /**
     * 通缉编号
     */ 	
	private String wantedid;
    /**
     * 赏金
     */ 	
	private String reward;
    /**
     * 案由
     */ 	
	private String cases;
    /**
     * 创建时间
     */ 	
	private String createtime;
    /**
     * 创建人员
     */ 	
	private String creator;
    /**
     * 浏览次数
     */ 	
	private Long views;
    /**
     * 头像地址
     */ 	
	private String headimg;
//    /**
//     * 头像图片
//     */ 	
//	private java.sql.Blob avatarPictures;
    /**
     * 黑名单类型0在逃人员1外贸公司逃逸
     */ 	
	private String blacklistType;
    /**
     * 逃匿通告标题
     */ 	
	private String noticetitle;
    /**
     * 公司名称
     */ 	
	private String companyname;
    /**
     * 公司地址
     */ 	
	private String companyaddress;
    /**
     * 逃匿时间
     */ 	
	private String escapedate;
    /**
     * 涉及金额
     */ 	
	private String amount;
    /**
     * 受害人数
     */ 	
	private Long victims;
    /**
     * 时间内容
     */ 	
	private String noticecontent;
    /**
     * bak
     */ 	
	private String bak;
    /**
     * 通报类型1外贸公司2采购商3供应商
     */ 	
	private String escapetype;
    /**
     * 注册号类型1工商注册号2社会信用代码
     */ 	
	private String regtype;
    /**
     * 注册号
     */ 	
	private String regid;
    /**
     * 修改时间
     */ 	
	private String updatetime;
    /**
     * 经营主体类型1公司2个人（20160411新增，征信公司接口使用）
     */ 	
	private String entitytype;
    /**
     * 更新标记 0--需更新  1--更新完成
     */ 	
	private String updateMark;
    /**
     * 头像图片是否存在  0--不存在 1--存在
     */ 	
	private String fileExises;

	
	public void setCustomerRowId(Long value) {
		this.customerRowId = value;
	}
	
	public Long getCustomerRowId() {
		return this.customerRowId;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setAccent(String value) {
		this.accent = value;
	}
	
	public String getAccent() {
		return this.accent;
	}
	
	public void setSex(String value) {
		this.sex = value;
	}
	
	public String getSex() {
		return this.sex;
	}
	
	public void setBirthday(String value) {
		this.birthday = value;
	}
	
	public String getBirthday() {
		return this.birthday;
	}
	
	public void setHeight(Long value) {
		this.height = value;
	}
	
	public Long getHeight() {
		return this.height;
	}
	
	public void setExname(String value) {
		this.exname = value;
	}
	
	public String getExname() {
		return this.exname;
	}
	
	public void setBody(String value) {
		this.body = value;
	}
	
	public String getBody() {
		return this.body;
	}
	
	public void setFace(String value) {
		this.face = value;
	}
	
	public String getFace() {
		return this.face;
	}
	
	public void setBodymark(String value) {
		this.bodymark = value;
	}
	
	public String getBodymark() {
		return this.bodymark;
	}
	
	public void setIdp(String value) {
		this.idp = value;
	}
	
	public String getIdp() {
		return this.idp;
	}
	
	public void setLv(String value) {
		this.lv = value;
	}
	
	public String getLv() {
		return this.lv;
	}
	
	public void setPublishtime(String value) {
		this.publishtime = value;
	}
	
	public String getPublishtime() {
		return this.publishtime;
	}
	
	public void setAddress(String value) {
		this.address = value;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setReporttel(String value) {
		this.reporttel = value;
	}
	
	public String getReporttel() {
		return this.reporttel;
	}
	
	public void setWantedid(String value) {
		this.wantedid = value;
	}
	
	public String getWantedid() {
		return this.wantedid;
	}
	
	public void setReward(String value) {
		this.reward = value;
	}
	
	public String getReward() {
		return this.reward;
	}
	
	public void setCases(String value) {
		this.cases = value;
	}
	
	public String getCases() {
		return this.cases;
	}
	
	public void setCreatetime(String value) {
		this.createtime = value;
	}
	
	public String getCreatetime() {
		return this.createtime;
	}
	
	public void setCreator(String value) {
		this.creator = value;
	}
	
	public String getCreator() {
		return this.creator;
	}
	
	public void setViews(Long value) {
		this.views = value;
	}
	
	public Long getViews() {
		return this.views;
	}
	
	public void setHeadimg(String value) {
		this.headimg = value;
	}
	
	public String getHeadimg() {
		return this.headimg;
	}
	
//	public void setAvatarPictures(java.sql.Blob value) {
//		this.avatarPictures = value;
//	}
//	
//	public java.sql.Blob getAvatarPictures() {
//		return this.avatarPictures;
//	}
	
	public void setBlacklistType(String value) {
		this.blacklistType = value;
	}
	
	public String getBlacklistType() {
		return this.blacklistType;
	}
	
	public void setNoticetitle(String value) {
		this.noticetitle = value;
	}
	
	public String getNoticetitle() {
		return this.noticetitle;
	}
	
	public void setCompanyname(String value) {
		this.companyname = value;
	}
	
	public String getCompanyname() {
		return this.companyname;
	}
	
	public void setCompanyaddress(String value) {
		this.companyaddress = value;
	}
	
	public String getCompanyaddress() {
		return this.companyaddress;
	}
	
	public void setEscapedate(String value) {
		this.escapedate = value;
	}
	
	public String getEscapedate() {
		return this.escapedate;
	}
	
	public void setAmount(String value) {
		this.amount = value;
	}
	
	public String getAmount() {
		return this.amount;
	}
	
	public void setVictims(Long value) {
		this.victims = value;
	}
	
	public Long getVictims() {
		return this.victims;
	}
	
	public void setNoticecontent(String value) {
		this.noticecontent = value;
	}
	
	public String getNoticecontent() {
		return this.noticecontent;
	}
	
	public void setBak(String value) {
		this.bak = value;
	}
	
	public String getBak() {
		return this.bak;
	}
	
	public void setEscapetype(String value) {
		this.escapetype = value;
	}
	
	public String getEscapetype() {
		return this.escapetype;
	}
	
	public void setRegtype(String value) {
		this.regtype = value;
	}
	
	public String getRegtype() {
		return this.regtype;
	}
	
	public void setRegid(String value) {
		this.regid = value;
	}
	
	public String getRegid() {
		return this.regid;
	}
	
	public void setUpdatetime(String value) {
		this.updatetime = value;
	}
	
	public String getUpdatetime() {
		return this.updatetime;
	}
	
	public void setEntitytype(String value) {
		this.entitytype = value;
	}
	
	public String getEntitytype() {
		return this.entitytype;
	}
	
	public void setUpdateMark(String value) {
		this.updateMark = value;
	}
	
	public String getUpdateMark() {
		return this.updateMark;
	}
	
	public void setFileExises(String value) {
		this.fileExises = value;
	}
	
	public String getFileExises() {
		return this.fileExises;
	}

	
}

