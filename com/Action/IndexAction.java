package com.Action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import com.db.*;
import com.superaction.*;

public class IndexAction extends MySuperAction {	// MySuperAction为自定义类，该类继承了ActionSupport类
	public static TreeMap searchMap;	//用来存储搜索条件			
	public static TreeMap typeMap;		//用来存储信息类别
	public String execute() throws Exception {		//实现Action类的execute()方法，该方法返回String型值
		/* 查询所有审核通过信息，按发布时间降序排列 */
		OpDB myOp=new OpDB();						//创建一个处理业务的OpDB类对象
		String sql1="select * from tb_info where (info_state='1') " +
				"order by info_date1 desc";
		List allsublist=myOp.OpListShow(sql1,null);		//调用业务对象中获取信息列表的方法，返回List对象
		request.setAttribute("allsublist",allsublist);		//保存List对象到request对象中
		session.put("typeMap",typeMap);					//保存typeMap对象
		session.put("searchMap",searchMap);		
		return SUCCESS;					//返回Action类中的最终静态常量SUCCESS，其值为success
	}
	static{								//静态代码块，在IndexAction类第一次被调用时执行
		OpDB myOp=new OpDB();
		/* 初始化所有信息类别 */
		String sql="select * from tb_type order by type_sign";
		typeMap=myOp.OpGetListBox(sql,null);	//调用业务对象中实现初始化信息类别的方法，返回TreeMap对象
		if(typeMap==null)
			typeMap=new TreeMap();
		/* 初始化搜索功能的下拉列表选项 */
		searchMap=new TreeMap();
		searchMap.put("ID值","id");
		searchMap.put("信息标题","info_title");
		searchMap.put("信息内容","info_content");
		searchMap.put("联系人","info_linkman");
		searchMap.put("联系电话","info_phone");
		searchMap.put("E-mail地址","info_email");
	}
}