package com.Action;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.superaction.InfoSuperAction;
import com.db.*;
import com.page.CreatePage;

public class InfoAction extends InfoSuperAction {	
	public String ListShow(){									//�����б���ʾĳ�����������Ϣ������
		request.setAttribute("mainPage","/page/listshow.jsp");	
		//������������ʾ������ʾ��ҳ��
		String infoType=request.getParameter("infoType");			//��ȡ��Ϣ���
		Object[] params={infoType};	
		OpDB myOp=new OpDB();								//����һ��ҵ�������		
		/* ��ȡ��ǰҳҪ��ʾ�������Ϣ */
		String sqlFreeAll="SELECT * FROM tb_info WHERE (info_type = ?) AND (info_state='1') ORDER BY info_date1 DESC";					//��ѯĳ����У����������Ϣ��SQL���
		String sqlFreeSub="";			//��ѯĳ����У�ĳһҳ��SQL���
		int perR=3;											//ÿҳ��ʾ3����¼
		String strCurrentP=request.getParameter("showpage");		//��ȡ�����д��ݵĵ�ǰҳ��
		String gowhich="Info/work/info_ListShow.action?infoType="+infoType;	//���÷�ҳ�������������Դ
		CreatePage createPage=myOp.OpCreatePage(sqlFreeAll, params,perR,strCurrentP,gowhich);	//����OpDB���е�OpCreatePage()����������ܼ�¼������ҳ�����������õ�ǰҳ�룬��Щ��Ϣ����װ����createPage������
		int top1=createPage.getPerR();							//��ȡÿҳ��ʾ��¼��
		int currentP=createPage.getCurrentP();					//��ȡ��ǰҳ��
		if(currentP==1){						//������ʾ��1ҳ��Ϣ��SQL���
			sqlFreeSub="SELECT TOP "+top1+" * FROM tb_info WHERE (info_type = ?) AND (info_state = '1') AND (info_payfor = '0') ORDER BY info_date DESC";
		}
		else{								//������ʾ����1ҳ�⣬����ָ��ҳ��Ϣ��SQl���
			int top2=(currentP-1)*top1;
			sqlFreeSub="SELECT TOP "+top1+" * FROM tb_info i WHERE (info_type = ?) AND (info_state = '1') AND (info_payfor = '0') AND (info_date < (SELECT MIN(info_date) FROM (SELECT TOP "+top2+" (info_date) FROM tb_info WHERE (info_type = i.info_type) AND (info_state = '1') AND (info_payfor = '0') ORDER BY info_date DESC) AS mindate)) ORDER BY info_date DESC";
		}		
		List onefreelist=myOp.OpListShow(sqlFreeSub, params);		//��ȡ��ǰҳҪ��ʾ�������Ϣ
		request.setAttribute("onefreelist",onefreelist);				//����onefreelist����
		request.setAttribute("createpage", createPage);				//�����װ�˷�ҳ��Ϣ��JavaBean����
		return SUCCESS;
	}

	
	public String SingleShow(){
		request.setAttribute("mainPage","/page/singleshow.jsp");		
		String id=request.getParameter("id");							//��ȡ�����д��ݵ���ϢID
		String sql="SELECT * FROM tb_info WHERE (id = ?)";			//���ɲ�ѯSQL���
		Object[] params={id};
		OpDB myOp=new OpDB();									//����һ��ҵ�������
		infoSingle=myOp.OpSingleShow(sql, params);				//��ȡҪ�鿴����Ϣ
		if(infoSingle==null){										//��Ϊnull����ʾҪ�鿴����Ϣ������
			request.setAttribute("mainPage","/pages/error.jsp");			//����Ҫ��ʾ��JSPҳ��
			addFieldError("SingleShowNoExist",getText("city.singleshow.no.exist"));			//������ʾ��Ϣ
		}		
		return SUCCESS;
	}


	public String Add(){		
		String addType=request.getParameter("addType");					//��ȡ���ʸ÷���������Ҫ���еĲ���
		if(addType==null||addType.equals("")){
			request.setAttribute("mainPage","/pages/add/addInfo.jsp");
			addType="linkTo";
		}
		if(addType.equals("add")){										//ִ����Ϣ��������
			request.setAttribute("mainPage","/pages/error.jsp");
			OpDB myOp=new OpDB();		
			Integer 	type=Integer.valueOf(infoSingle.getInfoType());			//��ȡ��Ϣ���
			String	title=infoSingle.getInfoTitle();						//��ȡ��Ϣ����
			String	content=DoString.HTMLChange(infoSingle.getInfoContent());	//ת����Ϣ�����е�HTML�ַ�
			String	phone=infoSingle.getInfoPhone();					//��ȡ��ϵ�绰
			phone = 	phone.replaceAll(",","��");						//�滻��,������			
			String	linkman=infoSingle.getInfoLinkman();				//��ȡ��ϵ��
			String	email=infoSingle.getInfoEmail();					//��ȡE-mail��ַ
			String	date1=DoString.dateTimeChange(new java.util.Date());		//��ȡ��ǰʱ�䲢ת��Ϊ�ַ�����ʽ
			String  date2=infoSingle.getInfoDate2();
			String	state="0";											//���������״̬Ϊ��0��
			String	peoplenum=infoSingle.getInfoPeopleNum();				//��������
			String	peoplefreenum=infoSingle.getInfoPeopleFreeNum();     //ʣ������
			Object[] params={type,title,content,linkman,phone,email,date1,state,peoplenum,peoplefreenum};
			String sql="insert into tb_info values(?,?,?,?,?,?,?,?,?,?)";		
			int i=myOp.OpUpdate(sql,params);				//����ҵ������OpUpdate()���������ݱ��в����¼
			if(i<=0)													//����ʧ��
				addFieldError("addE",getText("city.info.add.E"));			//����ʧ����ʾ��Ϣ
			else	{													//�����ɹ�
				sql="select * from tb_info where info_date1=?";				//���ɲ�ѯ�ոշ�����Ϣ��SQL���
				Object[] params1={date1};				
				int infoNum=myOp.OpSingleShow(sql, params1).getId();		//��ȡ�ոշ�����Ϣ��IDֵ
				addFieldError("addS",getText("city.info.add.S")+infoNum);		//����ɹ���ʾ��Ϣ
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
		if(showType.equals("link")){		//�Դӳ������л�ȡ�Ĳ�������ת�����
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
		CreatePage createPage=myOp.OpCreatePage(sqlSearchAll, params,perR,strCurrentP,gowhich);			//����OpDB���е�OpCreatePage()����������ܼ�¼������ҳ�����������õ�ǰҳ�룬��Щ��Ϣ����װ����createPage������
		
		int top1=createPage.getPerR();
		int currentP=createPage.getCurrentP();
		
		if(currentP==1){     		//��ʾ��1ҳ��Ϣ��SQL���
			sqlSearchSub="SELECT TOP "+top1+" * FROM tb_info WHERE ("+subsql+opname+"?) ORDER BY info_date DESC";
		}
		else{						//��ʾ����1ҳ�⣬����ָ��ҳ����Ϣ��SQl���
			int top2=(currentP-1)*top1;
			sqlSearchSub="SELECT TOP "+top1+" * FROM tb_info WHERE ("+subsql+opname+"?) AND (info_date < (SELECT MIN(info_date) FROM (SELECT TOP "+top2+" info_date FROM tb_info WHERE "+subsql+opname+"'"+param+"' ORDER BY info_date DESC) AS mindate)) ORDER BY info_date DESC";
//			sqlSearchSub="SELECT TOP "+top1+" * FROM tb_info WHERE ("+subsql+opname+"?) AND (info_date NOT IN (SELECT TOP "+top2+" info_date FROM tb_info WHERE "+subsql+opname+"'"+param+"' ORDER BY info_date DESC)) ORDER BY info_date DESC";				//��һ��ʵ�ַ�ҳ��ѯ��SQL���
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
			System.out.println("��ѡ��Ҫ��ѯ����Ϣ���");
			addFieldError("ListShowNoType",getText("city.info.listshow.no.infoType"));
		}
		else{
			try{
				Integer.parseInt(infoType);
			}catch(NumberFormatException e){
				System.out.println("��Ϣ��������ָ�ʽ��");
				addFieldError("ListShowWrongType",getText("city.info.listshow.format.infoType"));
				e.printStackTrace();
			}
		}
	}
	
	public void validateSingleShow(){
		request.setAttribute("mainPage","/pages/error.jsp");
		
		String id=request.getParameter("id");
		if(id==null&&id.equals("")){
			System.out.println("��ѡ��Ҫ�鿴����Ϣ��");
			addFieldError("SingleShowNoID",getText("city.info.singleshow.no.id"));
		}
		else{
			try{
				Integer.parseInt(id);
			}catch(NumberFormatException e){
				System.out.println("��ϢIDֵ�������ָ�ʽ��");
				addFieldError("ListShowWrongID",getText("city.info.listshow.format.infoID"));
				e.printStackTrace();
			}
		}
	}
	//�����Ϣʱ����֤������
	public void validateAdd(){	
		System.out.println("����ִ��validateAdd()������");
		request.setAttribute("mainPage","/pages/add/addInfo.jsp");
	
		String addType=request.getParameter("addType");	
		if(addType==null||addType.equals(""))
			addType="linkTo";
		
		if(addType.equals("add")){			
			int type=infoSingle.getInfoType();						//��ȡ��Ϣ��������
			String title=infoSingle.getInfoTitle();						//��ȡ��Ϣ���������
			String content=infoSingle.getInfoContent();				//��ȡ��Ϣ���ݱ�����
			String phone=infoSingle.getInfoPhone();					//��ȡ��ϵ�绰������
			String linkman=infoSingle.getInfoLinkman();				//��ȡ��ϵ�˱�����
			String email=infoSingle.getInfoEmail();					//��ȡe-mail��ַ������		
			
			boolean mark=true;			
			if(type<=0){
				mark=false;
				addFieldError("typeError",getText("city.info.no.infoType"));//��ȡproperties��Դ�ļ���keyָ���ļ�ֵ�洢������						
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
			if(mark){		//�������ݶ���Ϊ��
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
