package com.superaction;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;

public class MySuperAction extends ActionSupport implements SessionAware,
		ServletRequestAware, ServletResponseAware {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Map session; // session���������ΪMap

	public void setSession(Map session) { // �̳�SessionAware�ӿڱ���ʵ�ֵķ���
		this.session = session;
	}

	public void setServletRequest(HttpServletRequest request) { // �̳�ServletRequestAware�ӿڱ���ʵ�ֵķ���
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {// �̳�ServletResponseAware�ӿڱ���ʵ�ֵķ���
		this.response = response;
	}
}
