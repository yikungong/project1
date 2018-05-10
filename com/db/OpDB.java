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
		TreeMap typeMap=new TreeMap();	//创建一个TreeMap对象
		mydb.doPstm(sql, params);		//调用DB类的doPstm()方法查询数据库
		try {
			ResultSet rs=mydb.getRs();		//获取ResultSet结果集对象
			if(rs!=null){
				while(rs.next()){					//循环判断结果集中是否还存在记录		
					Integer sign=Integer.valueOf(rs.getInt("type_sign"));	//获取当前记录中type_sign字段内容
					String intro=rs.getString("type_intro");	//获取当前记录中type_intro字段内容
					typeMap.put(sign,intro);		//将获取的内容分别作为Map对象的key值与value值进行保存				
				}
				rs.close();				//关闭结果集
			}
		} catch (SQLException e) {			
			System.out.println("OpGetListBox()方法查询失败！");			
			e.printStackTrace();
		}finally{
			mydb.closed();			
		}
		return typeMap;	
	}
	public List OpListShow(String sql,Object[] params){
		List onelist=new ArrayList();
		mydb.doPstm(sql, params); 		//调用DB类的doPstm()方法查询数据库
		try{
			ResultSet rs=mydb.getRs();//获取ResultSet结果集对象
			if(rs!=null){
				while(rs.next()){
					InfoSingle infoSingle=new InfoSingle();	//创建一个信息类对象
					infoSingle.setId(rs.getInt("id"));           //id
					infoSingle.setInfoType(rs.getInt("info_type"));                //类型
					infoSingle.setInfoTitle(rs.getString("info_title"));              //标题
					infoSingle.setInfoContent(rs.getString("info_content"));           //活动内容
					infoSingle.setInfoLinkman(rs.getString("info_linkman"));          //联系人
					infoSingle.setInfoPhone(rs.getString("info_phone"));              //电话
					infoSingle.setInfoEmail(rs.getString("info_email"));              //email
					infoSingle.setInfoDate1(DoString.dateTimeChange(rs.getTimestamp("info_date1")));     //发布时间
					infoSingle.setInfoDate2(rs.getString("info_date2"));     //活动时间
					infoSingle.setInfoState(rs.getString("info_state"));                  //审核状态
					infoSingle.setInfoPeopleNum(rs.getString("info_peoplenum"));           //报名人数
					infoSingle.setInfoPeopleFreeNum(rs.getString("info_peoplefreenum"));    //报名剩余人数
					onelist.add(infoSingle);			//将信息类的对象保存到List集合对象中			
				}
			}
			rs.close();//关闭结果集对象
		}catch (Exception e){
			System.out.println("查看信息列表失败！(查询数据库)");			
			e.printStackTrace();
		}finally{
			mydb.closed();		//关闭数据库连接	
		}
		return onelist;		
	}
	
	public InfoSingle OpSingleShow(String sql,Object[] params){
		InfoSingle infoSingle=null;
		mydb.doPstm(sql, params);       		//调用DB类的doPstm()方法查询数据库
		try{
		    ResultSet rs=mydb.getRs();	//获取ResultSet结果集对象
			if(rs!=null&&rs.next()){//如果rs不为null，并且存在记录
				infoSingle=new InfoSingle(); //创建一个信息类对象
				infoSingle.setId(rs.getInt("id"));           //id
				infoSingle.setInfoType(rs.getInt("info_type"));                //类型
				infoSingle.setInfoTitle(rs.getString("info_title"));              //标题
				infoSingle.setInfoContent(rs.getString("info_content"));           //活动内容
				infoSingle.setInfoLinkman(rs.getString("info_linkman"));          //联系人
				infoSingle.setInfoPhone(rs.getString("info_phone"));              //电话
				infoSingle.setInfoEmail(rs.getString("info_email"));              //email
				infoSingle.setInfoDate1(DoString.dateTimeChange(rs.getTimestamp("info_date1")));     //发布时间
				infoSingle.setInfoDate2(DoString.dateTimeChange(rs.getTimestamp("info_date2")));     //活动时间
				infoSingle.setInfoState(rs.getString("info_state"));                  //审核状态
				infoSingle.setInfoPeopleNum(rs.getString("info_peoplenum"));           //报名人数
				infoSingle.setInfoPeopleFreeNum(rs.getString("info_peoplefreenum"));    //报名剩余人数				
				rs.close();	//关闭结果集对象			
			}
		}catch(Exception e){
			System.out.println("查看详细内容失败！(查询数据库)");
			e.printStackTrace();			
		}finally{
			mydb.closed();//关闭数据库连接
		}
		return infoSingle;
	}
	public int OpUpdate(String sql,Object[] params){		
		int i=-1;
		mydb.doPstm(sql, params);	//调用DB类的doPstm()方法更新数据库
		try{
			i=mydb.getCount();					//获取更新操作所影响的记录数	
		}catch(SQLException e){
			System.out.println("执行OpUpdate()方法失败！(更新数据库)");
			e.printStackTrace();
		}finally{
			mydb.closed();
		}
		return i;
	}
	public boolean LogOn(String sql,Object[] params){
		mydb.doPstm(sql, params);	//查询数据库获取结果集
		try {
			ResultSet rs=mydb.getRs();			//获取结果集		
			boolean mark=(rs==null||!rs.next()?false:true);	//判断用户是否存在，不存在返回false，存在返回true
			rs.close();
			return mark;			
		} catch (SQLException e) {
			System.out.println("登录失败！");
			e.printStackTrace();
			return false;
		}
		finally{
			mydb.closed();
		}
	}
	
	public CreatePage OpCreatePage(String sqlall,Object[] params,int perR,String strCurrentP,String gowhich){
		CreatePage page=new CreatePage();				//创建一个CreatePage类对象
		page.setPerR(perR);							//设置每页显示记录数
		if(sqlall!=null&&!sqlall.equals("")){
			DB mydb=new DB();
			mydb.doPstm(sqlall,params);			//查询数据库		
			try {
				ResultSet rs=mydb.getRs();		//获取查询结果集			
				if(rs!=null&&rs.next()){
					rs.last();					//将指针移动到结果集的最后一行
					page.setAllR(rs.getRow());		//调用getRow()方法获取当前记录行数(总记录数)，然后设置总记录数。
					page.setAllP();						//设置总页数
					page.setCurrentP(strCurrentP);			//设置当前页
					page.setPageInfo();						//设置分页状态信息
					page.setPageLink(gowhich);				//设置分页导航链接
					rs.close();								//关闭结果集
				}
			} catch (SQLException e) {
				System.out.println("OpDB.java/OpCreatePage()方法：创建CreatePage分页类失败！");
				e.printStackTrace();
			}finally{				
				mydb.closed();//关闭数据库连接
			}
		}		
		return page;
	}
}
