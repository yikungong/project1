package com.superaction;

import com.Single.InfoSingle;
import com.model.SearchInfo;

public class InfoSuperAction extends MySuperAction {
	protected InfoSingle infoSingle; //������װ�����ݱ��в�ѯ���ļ�¼�ͷ�����Ϣʱ�ı�����
	protected SearchInfo searchInfo;				//������װ����ʱ�ı�����

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
