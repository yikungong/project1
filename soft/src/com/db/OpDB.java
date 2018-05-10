package com.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.Single.*;
import com.page.*;


public class OpDB {
	private DB mydb;
	public OpDB(){
		mydb=new DB();	
	}
	public TreeMap OpGetListBox(String sql,Object[] params){
		TreeMap typeMap=new TreeMap();	//����һ��TreeMap����
		mydb.doPstm(sql, params);		//����DB���doPstm()������ѯ���ݿ�
		try {
			ResultSet rs=mydb.getRs();		//��ȡResultSet���������
			if(rs!=null){
				while(rs.next()){					//ѭ���жϽ�������Ƿ񻹴��ڼ�¼		
					Integer sign=Integer.valueOf(rs.getInt("type_sign"));	//��ȡ��ǰ��¼��type_sign�ֶ�����
					String intro=rs.getString("type_intro");	//��ȡ��ǰ��¼��type_intro�ֶ�����
					typeMap.put(sign,intro);		//����ȡ�����ݷֱ���ΪMap�����keyֵ��valueֵ���б���				
				}
				rs.close();				//�رս����
			}
		} catch (SQLException e) {			
			System.out.println("OpGetListBox()������ѯʧ�ܣ�");			
			e.printStackTrace();
		}finally{
			mydb.closed();			
		}
		return typeMap;	
	}
	public List OpListShow(String sql,Object[] params){
		List onelist=new ArrayList();
		mydb.doPstm(sql, params); 		//����DB���doPstm()������ѯ���ݿ�
		try{
			ResultSet rs=mydb.getRs();//��ȡResultSet���������
			if(rs!=null){
				while(rs.next()){
					InfoSingle infoSingle=new InfoSingle();	//����һ����Ϣ�����
					infoSingle.setId(rs.getInt("id"));           //id
					infoSingle.setInfoType(rs.getInt("info_type"));                //����
					infoSingle.setInfoTitle(rs.getString("info_title"));              //����
					infoSingle.setInfoContent(rs.getString("info_content"));           //�����
					infoSingle.setInfoLinkman(rs.getString("info_linkman"));          //��ϵ��
					infoSingle.setInfoPhone(rs.getString("info_phone"));              //�绰
					infoSingle.setInfoEmail(rs.getString("info_email"));              //email
					infoSingle.setInfoDate1(DoString.dateTimeChange(rs.getTimestamp("info_date1")));     //����ʱ��
					infoSingle.setInfoDate2(rs.getString("info_date2"));     //�ʱ��
					infoSingle.setInfoState(rs.getString("info_state"));                  //���״̬
					infoSingle.setInfoPeopleNum(rs.getString("info_peoplenum"));           //��������
					infoSingle.setInfoPeopleFreeNum(rs.getString("info_peoplefreenum"));    //����ʣ������
					onelist.add(infoSingle);			//����Ϣ��Ķ��󱣴浽List���϶�����			
				}
			}
			rs.close();//�رս��������
		}catch (Exception e){
			System.out.println("�鿴��Ϣ�б�ʧ�ܣ�(��ѯ���ݿ�)");			
			e.printStackTrace();
		}finally{
			mydb.closed();		//�ر����ݿ�����	
		}
		return onelist;		
	}
	
	public InfoSingle OpSingleShow(String sql,Object[] params){
		InfoSingle infoSingle=null;
		mydb.doPstm(sql, params);       		//����DB���doPstm()������ѯ���ݿ�
		try{
		    ResultSet rs=mydb.getRs();	//��ȡResultSet���������
			if(rs!=null&&rs.next()){//���rs��Ϊnull�����Ҵ��ڼ�¼
				infoSingle=new InfoSingle(); //����һ����Ϣ�����
				infoSingle.setId(rs.getInt("id"));           //id
				infoSingle.setInfoType(rs.getInt("info_type"));                //����
				infoSingle.setInfoTitle(rs.getString("info_title"));              //����
				infoSingle.setInfoContent(rs.getString("info_content"));           //�����
				infoSingle.setInfoLinkman(rs.getString("info_linkman"));          //��ϵ��
				infoSingle.setInfoPhone(rs.getString("info_phone"));              //�绰
				infoSingle.setInfoEmail(rs.getString("info_email"));              //email
				infoSingle.setInfoDate1(DoString.dateTimeChange(rs.getTimestamp("info_date1")));     //����ʱ��
				infoSingle.setInfoDate2(DoString.dateTimeChange(rs.getTimestamp("info_date2")));     //�ʱ��
				infoSingle.setInfoState(rs.getString("info_state"));                  //���״̬
				infoSingle.setInfoPeopleNum(rs.getString("info_peoplenum"));           //��������
				infoSingle.setInfoPeopleFreeNum(rs.getString("info_peoplefreenum"));    //����ʣ������				
				rs.close();	//�رս��������			
			}
		}catch(Exception e){
			System.out.println("�鿴��ϸ����ʧ�ܣ�(��ѯ���ݿ�)");
			e.printStackTrace();			
		}finally{
			mydb.closed();//�ر����ݿ�����
		}
		return infoSingle;
	}
	public int OpUpdate(String sql,Object[] params){		
		int i=-1;
		mydb.doPstm(sql, params);	//����DB���doPstm()�����������ݿ�
		try{
			i=mydb.getCount();					//��ȡ���²�����Ӱ��ļ�¼��	
		}catch(SQLException e){
			System.out.println("ִ��OpUpdate()����ʧ�ܣ�(�������ݿ�)");
			e.printStackTrace();
		}finally{
			mydb.closed();
		}
		return i;
	}
	public boolean LogOn(String sql,Object[] params){
		mydb.doPstm(sql, params);	//��ѯ���ݿ��ȡ�����
		try {
			ResultSet rs=mydb.getRs();			//��ȡ�����		
			boolean mark=(rs==null||!rs.next()?false:true);	//�ж��û��Ƿ���ڣ������ڷ���false�����ڷ���true
			rs.close();
			return mark;			
		} catch (SQLException e) {
			System.out.println("��¼ʧ�ܣ�");
			e.printStackTrace();
			return false;
		}
		finally{
			mydb.closed();
		}
	}
	
	public CreatePage OpCreatePage(String sqlall,Object[] params,int perR,String strCurrentP,String gowhich){
		CreatePage page=new CreatePage();				//����һ��CreatePage�����
		page.setPerR(perR);							//����ÿҳ��ʾ��¼��
		if(sqlall!=null&&!sqlall.equals("")){
			DB mydb=new DB();
			mydb.doPstm(sqlall,params);			//��ѯ���ݿ�		
			try {
				ResultSet rs=mydb.getRs();		//��ȡ��ѯ�����			
				if(rs!=null&&rs.next()){
					rs.last();					//��ָ���ƶ�������������һ��
					page.setAllR(rs.getRow());		//����getRow()������ȡ��ǰ��¼����(�ܼ�¼��)��Ȼ�������ܼ�¼����
					page.setAllP();						//������ҳ��
					page.setCurrentP(strCurrentP);			//���õ�ǰҳ
					page.setPageInfo();						//���÷�ҳ״̬��Ϣ
					page.setPageLink(gowhich);				//���÷�ҳ��������
					rs.close();								//�رս����
				}
			} catch (SQLException e) {
				System.out.println("OpDB.java/OpCreatePage()����������CreatePage��ҳ��ʧ�ܣ�");
				e.printStackTrace();
			}finally{				
				mydb.closed();//�ر����ݿ�����
			}
		}		
		return page;
	}
}
