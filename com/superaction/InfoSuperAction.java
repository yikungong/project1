package com.superaction;

import com.Single.InfoSingle;
import com.model.SearchInfo;

public class InfoSuperAction extends MySuperAction {
	protected InfoSingle infoSingle; //用来封装从数据表中查询出的记录和发布信息时的表单数据
	protected SearchInfo searchInfo;				//用来封装搜索时的表单数据

	public InfoSingle getInfoSingle() {
		return infoSingle;
	}
	public void setInfoSingle(InfoSingle infoSingle) {
		this.infoSingle = infoSingle;
	}
	public SearchInfo getSearchInfo() {
		return searchInfo;
	}
	public void setSearchInfo(SearchInfo searchInfo) {
		this.searchInfo = searchInfo;
	}	
}
