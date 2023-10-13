package com.kh.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {

	// 1. Connection 객체 생성한 후 Connection 객체 반환시켜주는 getConnection메소드
	public static Connection getConnection() {
		Connection conn = null;
		Properties prop = new Properties();
		
		//읽어들이고자 하는 classes폴더내의 driver.properties파일의 물리적인 경로 가져오기
		String filePath = JDBCTemplate.class.getResource("/db/driver/driver.properties").getPath();
		System.out.println(filePath);
			
		try {
			
			prop.load(new FileInputStream(filePath));
			
			//jdbc driver등록
			Class.forName(prop.getProperty("driver"));
			
			
			//접속하고자 하는 db의 url, 계정명, 비밀번호 제시해서 Connection 객체 생성
			conn = DriverManager.getConnection(prop.getProperty("url"),
											prop.getProperty("username"),   
											prop.getProperty("password"));
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return conn;
		
		
	}
	
	// 2. Connection객체 전달받아서 commit 처리해주는 메소드
	public static void commit(Connection conn) {
			try {
				if (conn != null && !conn.isClosed()) {
				conn.commit();
				} 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	// 3. Connection객체 전달받아서 rollback 처리해주는 메소드

	public static void rollback(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
			conn.rollback();
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 4. Connection객체 전달받아서 반납시켜주는 메소드

	public static void close(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
			conn.close();
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 5. Statement관련 객체 전달받아서 반납시켜주는 메소드
	public static void close(Statement stmt) {
		try {
			if (stmt != null && !stmt.isClosed()) {
				stmt.close();
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 6. ResultSet객체에 전달받아서 반납시켜주는 메소드
	public static void close(ResultSet rset) {
		try {
			if (rset != null && !rset.isClosed()) {
				rset.close();
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
