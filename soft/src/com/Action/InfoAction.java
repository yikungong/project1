package com.Action;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.superaction.InfoSuperAction;
import com.db.*;
import com.page.CreatePage;

public class InfoAction extends InfoSuperAction {	
	public String ListShow(){									//处理列表显示某类别中所有信息的请求
		request.setAttribute("mainPage","/page/listshow.jsp");	
		//设置在内容显示区中显示的页面
		String infoType=request.getParameter("infoType");			//获取信息类别
		Object[] params={infoType};	
		OpDB myOp=new OpDB();								//创建一个业务处理对象		
		/* 获取当前页要显示的免费信息 */
		String sqlFreeAll="SELECT * FROM tb_info WHERE (info_type = ?) AND (info_state='1') ORDER BY info_date1 DESC";					//查询某类别中，所有免费信息的SQL语句
		String sqlFreeSub="";			//查询某类别中，某一页的SQL语句
		int perR=3;											//每页显示3条记录
		String strCurrentP=request.getParameter("showpage");		//获取请求中传递的当前页码
		String gowhich="Info/work/info_ListShow.action?infoType="+infoType;	//设置分页超链接请求的资源
		CreatePage createPage=myOp.OpCreatePage(sqlFreeAll, params,perR,strCurrentP,gowhich);	//调用OpDB类中的OpCreatePage()方法计算出总记录数、总页数，并且设置当前页码，这些信息都封装到了createPage对象中
		int top1=createPage.getPerR();							//获取每页显示记录数
		int currentP=createPage.getCurrentP();					//获取当前页码
		if(currentP==1){						//设置显示第1页信息的SQL语句
			sqlFreeSub="SELECT TOP "+top1+" * FROM tb_info WHERE (info_type = ?) AND (info_state = '1') AND (info_payfor = '0') ORDER BY info_date DESC";
		}
		else{								//设置显示除第1页外，其他指定页信息的SQl语句
			int top2=(currentP-1)*top1;
			sqlFreeSub="SELECT TOP "+top1+" * FROM tb_info i WHERE (info_type = ?) AND (info_state = '1') AND (info_payfor = '0') AND (info_date < (SELECT MIN(info_date) FROM (SELECT TOP "+top2+" (info_date) FROM tb_info WHERE (info_type = i.info_type) AND (info_state = '1') AND (info_payfor = '0') ORDER BY info_date DESC) AS mindate)) ORDER BY info_date DESC";
		}		
		List onefreelist=myOp.OpListShow(sqlFreeSub, params);		//获取当前页要显示的免费信息
		request.setAttribute("onefreelist",onefreelist);				//保存onefreelist对象
		request.setAttribute("createpage", createPage);				//保存封装了分页信息的JavaBean对象
		return SUCCESS;
	}

	
	public String SingleShow(){
		request.setAttribute("mainPage","/page/singleshow.jsp");		
		String id=request.getParameter("id");							//获取请求中传递的信息ID
		String sql="SELECT * FROM tb_info WHERE (id = ?)";			//生成查询SQL语句
		Object[] params={id};
		OpDB myOp=new OpDB();									//创建一个业务处理对象
		infoSingle=myOp.OpSingleShow(sql, params);				//获取要查看的信息
		if(infoSingle==null){										//若为null，表示要查看的信息不存在
			request.setAttribute("mainPage","/pages/error.jsp");			//设置要显示的JSP页面
			addFieldError("SingleShowNoExist",getText("city.singleshow.no.exist"));			//设置提示信息
		}		
		return SUCCESS;
	}


	public String Add(){		
		String addType=request.getParameter("addType");					//获取访问该方法的请求要进行的操作
		if(addType==null||addType.equals("")){
			request.setAttribute("mainPage","/pages/add/addInfo.jsp");
			addType="linkTo";
		}
		if(addType.equals("add")){										//执行信息发布操作
			request.setAttribute("mainPage","/pages/error.jsp");
			OpDB myOp=new OpDB();		
			Integer 	type=Integer.valueOf(infoSingle.getInfoType());			//获取信息类别
			String	title=infoSingle.getInfoTitle();						//获取信息标题
			String	content=DoString.HTMLChange(infoSingle.getInfoContent());	//转换信息内容中的HTML字符
			String	phone=infoSingle.getInfoPhone();					//获取联系电话
			phone = 	phone.replaceAll(",","●");						//替换“,”符号			
			String	linkman=infoSingle.getInfoLinkman();				//获取联系人
			String	email=infoSingle.getInfoEmail();					//获取E-mail地址
			String	date1=DoString.dateTimeChange(new java.util.Date());		//获取当前时间并转换为字符串格式
			String  date2=infoSingle.getInfoDate2();
			String	state="0";											//设置已审核状态为“0”
			String	peoplenum=infoSingle.getInfoPeopleNum();				//报名人数
			String	peoplefreenum=infoSingle.getInfoPeopleFreeNum();     //剩余人数
			Object[] params={type,title,content,linkman,phone,email,date1,state,peoplenum,peoplefreenum};
			String sql="insert into tb_info values(?,?,?,?,?,?,?,?,?,?)";		
			int i=myOp.OpUpdate(sql,params);				//调用业务对象的OpUpdate()方法向数据表中插入记录
			if(i<=0)													//操作失败
				addFieldError("addE",getText("city.info.add.E"));			//保存失败提示信息
			else	{													//操作成功
				sql="select * from tb_info where info_date1=?";				//生成查询刚刚发布信息的SQL语句
				Object[] params1={date1};				
				int infoNum=myOp.OpSingleShow(sql, params1).getId();		//获取刚刚发布信息的ID值
				addFieldError("addS",getText("city.info.add.S")+infoNum);		//保存成功提示信息
			}
		}		
		return SUCCESS;
	}

	
	public String SearchShow() throws UnsupportedEncodingException{
		request.setAttribute("mainPage","/page/searchshow.jsp");		
		
		String subsql=searchInfo.getSubsql();
		String sqlvalue=searchInfo.getSqlvalue();		
		String type=searchInfo.getType();
		
		String showType=request.getParameter("showType");
		if(showType==null)
			showType="";
		if(showType.equals("link")){		//对从超链接中获取的参数进行转码操作
			try {
				sqlvalue=new String(sqlvalue.getBytes("ISO-8859-1"),"gb2312");
			} catch (UnsupportedEncodingException e) {			
				sqlvalue="";
				e.printStackTrace();
			}
			searchInfo.setSqlvalue(sqlvalue);
		}
		
		session.put("subsql",subsql);
		session.put("sqlvalue",sqlvalue);
		session.put("type",type);
		
		String param="";
		String opname="";
		if(type.equals("like")){
			opname=" LIKE ";
			param="%"+sqlvalue+"%";			
		}
		else{		
			opname=" = ";
			param=sqlvalue;			
		}
		
		String sqlSearchAll="SELECT * FROM tb_info WHERE ("+subsql+opname+"?) ORDER BY info_date DESC";
		String sqlSearchSub="";		
		Object[] params={param};		

		int perR=8;
		String strCurrentP=request.getParameter("showpage");
		String gowhich = "info_SearchShow.action?searchInfo.subsql="+subsql+"&searchInfo.sqlvalue="+sqlvalue+"&searchInfo.type="+type+"&showType=link";
		
		OpDB myOp=new OpDB();
		CreatePage createPage=myOp.OpCreatePage(sqlSearchAll, params,perR,strCurrentP,gowhich);			//调用OpDB类中的OpCreatePage()方法计算出总记录数、总页数，并且设置当前页码，这些信息都封装到了createPage对象中
		
		int top1=createPage.getPerR();
		int currentP=createPage.getCurrentP();
		
		if(currentP==1){     		//显示第1页信息的SQL语句
			sqlSearchSub="SELECT TOP "+top1+" * FROM tb_info WHERE ("+subsql+opname+"?) ORDER BY info_date DESC";
		}
		else{						//显示除第1页外，其他指定页码信息的SQl语句
			int top2=(currentP-1)*top1;
			sqlSearchSub="SELECT TOP "+top1+" * FROM tb_info WHERE ("+subsql+opname+"?) AND (info_date < (SELECT MIN(info_date) FROM (SELECT TOP "+top2+" info_date FROM tb_info WHERE "+subsql+opname+"'"+param+"' ORDER BY info_date DESC) AS mindate)) ORDER BY info_date DESC";
//			sqlSearchSub="SELECT TOP "+top1+" * FROM tb_info WHERE ("+subsql+opname+"?) AND (info_date NOT IN (SELECT TOP "+top2+" info_date FROM tb_info WHERE "+subsql+opname+"'"+param+"' ORDER BY info_date DESC)) ORDER BY info_date DESC";				//另一种实现分页查询的SQL语句
		}
		
		List searchlist=myOp.OpListShow(sqlSearchSub, params);
		request.setAttribute("searchlist",searchlist);
		request.setAttribute("createpage", createPage);
		
		return SUCCESS;
	}
	
	public void validateListShow(){
		request.setAttribute("mainPage","/pages/error.jsp");
		
		String infoType=request.getParameter("infoType");
		if(infoType==null&&infoType.equals("")){
			System.out.println("请选择要查询的信息类别！");
			addFieldError("ListShowNoType",getText("city.info.listshow.no.infoType"));
		}
		else{
			try{
				Integer.parseInt(infoType);
			}catch(NumberFormatException e){
				System.out.println("信息类别不是数字格式！");
				addFieldError("ListShowWrongType",getText("city.info.listshow.format.infoType"));
				e.printStackTrace();
			}
		}
	}
	
	public void validateSingleShow(){
		request.setAttribute("mainPage","/pages/error.jsp");
		
		String id=request.getParameter("id");
		if(id==null&&id.equals("")){
			System.out.println("请选择要查看的信息！");
			addFieldError("SingleShowNoID",getText("city.info.singleshow.no.id"));
		}
		else{
			try{
				Integer.parseInt(id);
			}catch(NumberFormatException e){
				System.out.println("信息ID值不是数字格式！");
				addFieldError("ListShowWrongID",getText("city.info.listshow.format.infoID"));
				e.printStackTrace();
			}
		}
	}
	//添加信息时，验证表单数据
	public void validateAdd(){	
		System.out.println("正在执行validateAdd()方法…");
		request.setAttribute("mainPage","/pages/add/addInfo.jsp");
	
		String addType=request.getParameter("addType");	
		if(addType==null||addType.equals(""))
			addType="linkTo";
		
		if(addType.equals("add")){			
			int type=infoSingle.getInfoType();						//获取信息类别表单数据
			String title=infoSingle.getInfoTitle();						//获取信息标题表单数据
			String content=infoSingle.getInfoContent();				//获取信息内容表单数据
			String phone=infoSingle.getInfoPhone();					//获取联系电话表单数据
			String linkman=infoSingle.getInfoLinkman();				//获取联系人表单数据
			String email=infoSingle.getInfoEmail();					//获取e-mail地址表单数据		
			
			boolean mark=true;			
			if(type<=0){
				mark=false;
				addFieldError("typeError",getText("city.info.no.infoType"));//获取properties资源文件中key指定的键值存储的内容						
			}
			if(title==null||title.equals("")){
				mark=false;
				addFieldError("titleError",getText("city.info.no.infoTitle"));
			}
			if(content==null||content.equals("")){
				mark=false;
				addFieldError("contentError",getText("city.info.no.infoContent"));
			}
			if(phone==null||phone.equals("")){
				mark=false;
				addFieldError("phoneError",getText("city.info.no.infoPhone"));
			}
			if(linkman==null||linkman.equals("")){
				mark=false;
				addFieldError("linkmanError",getText("city.info.no.infoLinkman"));
			}
			if(email==null||email.equals("")){
				mark=false;
				addFieldError("emailError",getText("city.info.no.infoEmail"));
			}
			if(mark){		//若表单数据都不为空
				String phoneRegex="(\\d{3}-)\\d{8}|(\\d{4}-)(\\d{7}|\\d{8})|\\d{11}";
				if(phone.indexOf(",")<0){
					if(!infoSingle.getInfoPhone().matches(phoneRegex)){
						addFieldError("phoneError",getText("city.info.format.infoPhone"));
					}					
				}
				else{
					String[] phones=phone.split(",");
					for(int i=0;i<phones.length;i++){
						if(!phones[i].matches(phoneRegex)){							
							addFieldError("phoneError",getText("city.info.format.infoPhone"));							
							break;
						}
					}
				}
				String emailRegex="\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
				if(!infoSingle.getInfoEmail().matches(emailRegex)){
					addFieldError("emailError",getText("city.info.format.infoEmail"));
				}				
			}
		}
	}

	public void validateSearchShow() {
		request.setAttribute("mainPage","/pages/error.jsp");		
		String subsql=searchInfo.getSubsql();
		String sqlvalue=searchInfo.getSqlvalue();
		String type=searchInfo.getType();
		
		if(subsql==null||subsql.equals("")){
			addFieldError("SearchNoC",getText("city.info.search.no.condition"));
		}
		if(sqlvalue==null||sqlvalue.equals("")){
			addFieldError("SearchNoV",getText("city.info.search.no.value"));
		}
		if(type==null||type.equals("")){
			addFieldError("SearchNoT",getText("city.info.search.no.type"));
		}
	}
}
