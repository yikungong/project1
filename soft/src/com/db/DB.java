package com.db;
import java.sql.*;
public class DB {
		private Connection con;           //connection对象
		private PreparedStatement pstm;   //........对象
		private String user="root";
		private String password="root";
		private String className="com.mysql.jdbc.Driver";
		private String url="jdbc::mysql://localhost:3306/db_book";
		public DB(){
			try{
				Class.forName(className);
			}catch(ClassNotFoundException e){
				System.out.println("加载失败");
				e.printStackTrace();
			}
		}
		public Connection getCon(){               //获取链接
			try{
				con=DriverManager.getConnection(url,user,password);
			}catch(SQLException e){
				System.out.println("链接失败");
				con=null;
				e.printStackTrace();
			}
			return con;
		}
		public void doPstm(String sql,Object[] params){                      //对数据库增删查改
			if(sql!=null && !sql.equals("")){
				if (params == null )params = new Object[0];           //新建object
				getCon();
				if(con!=null){
					try{
						pstm=con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
						for(int i=0;i<params.length;i++){
							pstm.setObject(i+1,params[i]);
						}
						pstm.execute();
					}catch(SQLException e){
						System.out.println("doPostm方法错误！");          
						e.printStackTrace();
					}
				}
			}
		}
		public ResultSet getRs() throws SQLException{                     //放回ResultSet对象
			return pstm.getResultSet();
		}
		public int getCount() throws SQLException{                          //返回受getResultSet（）影响的记录数
			return pstm.getUpdateCount();
		}
		public void closed(){
			try{
				if(pstm!=null)
					pstm.close();			
			}catch(SQLException e){
				System.out.println("关闭pstm对象失败！");
				e.printStackTrace();
			}
			try{
				if(con!=null){
					con.close();
				}
			}catch(SQLException e){
				System.out.println("关闭con对象失败！");
				e.printStackTrace();
			}
		}
}
