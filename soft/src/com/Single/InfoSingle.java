package com.Single;

public class InfoSingle {                        //活动信息类
	private int id; // 活动ID
	private String username; //报名用户
	private int infoType; // 活动类型
	private String infoTitle; // 活动标题
	private String infoContent; // 活动内容
	private String infoLinkman; // 发布人
	private String infoPhone; // 联系电话
	private String infoEmail; // E-mail地址
	private String infoDate1; // 信息发布时间
	private String infoDate2; // 活动时间
	private String infoState; // 信息审核状态
	private String infoPeopleNum; // 活动报名人数
	private String infoPeopleFreeNum; // 活动报名剩余人数
	
	public int getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInfoType() {
		return infoType;
	}
	public void setInfoType(int infoType) {
		this.infoType = infoType;
	}
	public String getInfoTitle() {
		return infoTitle;
	}
	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}
	public String getInfoContent() {
		return infoContent;
	}
	public void setInfoContent(String infoContent) {
		this.infoContent = infoContent;
	}
	public String getInfoLinkman() {
		return infoLinkman;
	}
	public void setInfoLinkman(String infoLinkman) {
		this.infoLinkman = infoLinkman;
	}
	public String getInfoPhone() {
		return infoPhone;
	}
	public void setInfoPhone(String infoPhone) {
		this.infoPhone = infoPhone;
	}
	public String getInfoEmail() {
		return infoEmail;
	}
	public void setInfoEmail(String infoEmail) {
		this.infoEmail = infoEmail;
	}
	public String getInfoDate1() {
		return infoDate1;
	}
	public void setInfoDate1(String infoDate1) {
		this.infoDate1 = infoDate1;
	}
	public String getInfoDate2() {
		return infoDate2;
	}
	public void setInfoDate2(String infoDate2) {
		this.infoDate2 = infoDate2;
	}
	public String getInfoState() {
		return infoState;
	}
	public void setInfoState(String infoState) {
		this.infoState = infoState;
	}
	public String getInfoPeopleNum() {
		return infoPeopleNum;
	}
	public void setInfoPeopleNum(String infoPeopleNum) {
		this.infoPeopleNum = infoPeopleNum;
	}
	public String getInfoPeopleFreeNum() {
		return infoPeopleFreeNum;
	}
	public void setInfoPeopleFreeNum(String infoPeopleFreeNum) {
		this.infoPeopleFreeNum = infoPeopleFreeNum;
	}

	
	
}