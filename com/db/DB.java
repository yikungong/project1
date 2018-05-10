package com.db;
import java.sql.*;
public class DB {
		private Connection con;           //connection����
		private PreparedStatement pstm;   //........����
		private String user="root";
		private String password="root";
		private String className="com.mysql.jdbc.Driver";
		private String url="jdbc::mysql://localhost:3306/db_book";
		public DB(){
			try{
				Class.forName(className);
			}catch(ClassNotFoundException e){
				System.out.println("����ʧ��");
				e.printStackTrace();
			}
		}
		public Connection getCon(){               //��ȡ����
			try{
				con=DriverManager.getConnection(url,user,password);
			}catch(SQLException e){
				System.out.println("����ʧ��");
				con=null;
				e.printStackTrace();
			}
			return con;
		}
		public void doPstm(String sql,Object[] params){                      //�����ݿ���ɾ���
			if(sql!=null && !sql.equals("")){
				if (params == null )params = new Object[0];           //�½�object
				getCon();
				if(con!=null){
					try{
						pstm=con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
						for(int i=0;i<params.length;i++){
							pstm.setObject(i+1,params[i]);
						}
						pstm.execute();
					}catch(SQLException e){
						System.out.println("doPostm��������");          
						e.printStackTrace();
					}
				}
			}
		}
		public ResultSet getRs() throws SQLException{                     //�Ż�ResultSet����
			return pstm.getResultSet();
		}
		public int getCount() throws SQLException{                          //������getResultSet����Ӱ��ļ�¼��
			return pstm.getUpdateCount();
		}
		public void closed(){
			try{
				if(pstm!=null)
					pstm.close();			
			}catch(SQLException e){
				System.out.println("�ر�pstm����ʧ�ܣ�");
				e.printStackTrace();
			}
			try{
				if(con!=null){
					con.close();
				}
			}catch(SQLException e){
				System.out.println("�ر�con����ʧ�ܣ�");
				e.printStackTrace();
			}
		}
}
