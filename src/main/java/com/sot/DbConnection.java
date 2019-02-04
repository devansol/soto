package com.sot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.spi.DirStateFactory.Result;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.mysql.jdbc.PreparedStatement;

public class DbConnection {
	
	@Autowired
	private DataSource dataSource;
	
	protected  Connection conn = null;
	
	public void connect() throws Exception{
		try {
			this.conn = dataSource.getConnection();
		} catch ( Exception e){
			e.printStackTrace();
			throw new Exception(e);
		} 
	}
	
	public Connection getConnection() throws Exception{
		try {
			if(this.conn == null)
				this.connect();
			else if(this.conn.isClosed())
				this.connect();			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return this.conn;
	}
	
	public void closeConnection() throws Exception{
		try {
			if(conn != null){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
//	public static void main(String[] args) throws Exception{
//		try{
//			 String url ="jdbc:mysql://localhost/sot_online";
//	         String user="root";
//	            String pass="";
//	            Class.forName("com.mysql.jdbc.Driver");
//	            conn = DriverManager.getConnection(url,user,pass);
//	            ps = (PreparedStatement) conn.prepareStatement("select curdate()");
//	            rs = ps.executeQuery();
//	            if(rs.next()){
//	            	System.out.println("Koneksi Berhasil");
//	            }
//		}catch (Exception e) {
//			throw new Exception(e.getMessage());
//		}finally {
//			conn.close();
//		}
//	}
}
