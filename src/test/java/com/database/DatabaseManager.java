package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.api.utils.ConfigManager;

public class DatabaseManager {

	private static final String DB_URL = ConfigManager.getProperty("DB_URL");
	private static final String DB_USER_NAME = ConfigManager.getProperty("DB_USER_NAME");
	private static final String DB_PASSWORD = ConfigManager.getProperty("DB_USER_PASSWORD");
	private volatile static Connection conn; // Any update that happens to this conn variable|
	// all threads will be aware of it!!
	
	private DatabaseManager() {
		
	}
	
	public synchronized static void createConnection() throws SQLException {
		
		if(conn == null) { // First Check which all the parallel threads will enter
		synchronized (DatabaseManager.class) {
			if (conn==null) {
				conn = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);		
				System.out.println(conn);		
			}
		}	
	}
	}
}
