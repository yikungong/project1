package com.Action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import com.db.*;
import com.superaction.*;

public class IndexAction extends MySuperAction {	// MySuperActionΪ�Զ����࣬����̳���ActionSupport��
	public static TreeMap searchMap;	//�����洢��������			
	public static TreeMap typeMap;		//�����洢��Ϣ���
	public String execute() throws Exception {		//ʵ��Action���execute()�������÷�������String��ֵ
		/* ��ѯ�������ͨ����Ϣ��������ʱ�併������ */
		OpDB myOp=new OpDB();						//����һ������ҵ���OpDB�����
		String sql1="select * from tb_info where (info_state='1') " +
				"order by info_date1 desc";
		List allsublist=myOp.OpListShow(sql1,null);		//����ҵ������л�ȡ��Ϣ�б�ķ���������List����
		request.setAttribute("allsublist",allsublist);		//����List����request������
		session.put("typeMap",typeMap);					//����typeMap����
		session.put("searchMap",searchMap);		
		return SUCCESS;					//����Action���е����վ�̬����SUCCESS����ֵΪsuccess
	}
	static{								//��̬����飬��IndexAction���һ�α�����ʱִ��
		OpDB myOp=new OpDB();
		/* ��ʼ��������Ϣ��� */
		String sql="select * from tb_type order by type_sign";
		typeMap=myOp.OpGetListBox(sql,null);	//����ҵ�������ʵ�ֳ�ʼ����Ϣ���ķ���������TreeMap����
		if(typeMap==null)
			typeMap=new TreeMap();
		/* ��ʼ���������ܵ������б�ѡ�� */
		searchMap=new TreeMap();
		searchMap.put("IDֵ","id");
		searchMap.put("��Ϣ����","info_title");
		searchMap.put("��Ϣ����","info_content");
		searchMap.put("��ϵ��","info_linkman");
		searchMap.put("��ϵ�绰","info_phone");
		searchMap.put("E-mail��ַ","info_email");
	}
}