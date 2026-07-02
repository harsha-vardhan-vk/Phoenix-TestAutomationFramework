package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCDemo {

	public static void main(String[] args) throws SQLException {
		// Step1 : Establish the Connection to the Phoenix Database
		Connection conn = DriverManager.getConnection("jdbc:mysql://64.227.160.186 :3306/SR_DEV", "srdev_ro_automation", "Srdev@123");
	
		Statement statement=conn.createStatement();

		ResultSet resultSet= statement.executeQuery("SELECT tc.first_name, tc.last_name, tc.mobile_number from tr_customer tc;");
	
		while (resultSet.next()) {
			String firstName = resultSet.getString("first_name");
			String lastName = resultSet.getString("last_name");
			String mobileNumber = resultSet.getString("mobile_number");
			System.out.println(firstName+"|"+lastName+"|"+mobileNumber);
		}
	}

}
