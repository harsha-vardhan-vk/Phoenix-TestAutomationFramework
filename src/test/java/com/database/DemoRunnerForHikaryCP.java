package com.database;

import java.sql.Connection;
import java.sql.SQLException;

public class DemoRunnerForHikaryCP {

	public static void main(String[] args) throws SQLException {
		Connection conn = DatabaseManagerForHikariCP.getConnection();
		System.out.println(conn);
	}

}
