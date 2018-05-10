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
	protected Map session; // session对象的类型为Map

	public void setSession(Map session) { // 继承SessionAware接口必须实现的方法
		this.session = session;
	}

	public void setServletRequest(HttpServletRequest request) { // 继承ServletRequestAware接口必须实现的方法
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {// 继承ServletResponseAware接口必须实现的方法
		this.response = response;
	}
}
