package com.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.api.utils.ConfigManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCPDemo {

	public static void main(String[] args) throws SQLException {

		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl(ConfigManager.getProperty("DB_URL"));
		hikariConfig.setUsername(ConfigManager.getProperty("DB_USER_NAME"));
		hikariConfig.setPassword(ConfigManager.getProperty("DB_USER_PASSWORD"));
		hikariConfig.setMaximumPoolSize(10);
		hikariConfig.setMinimumIdle(2);
		hikariConfig.setConnectionTimeout(1000); // 10secs
		hikariConfig.setIdleTimeout(1000); // 10secs
		hikariConfig.setMaxLifetime(1800000); // 30 mins
		hikariConfig.setPoolName("Phoenix Test Automation Framework Pool");
		
		HikariDataSource ds = new HikariDataSource(hikariConfig);
		Connection conn = ds.getConnection();
		System.out.println("Connection established: " + conn);

		Statement statement = conn.createStatement();
		ResultSet rs = statement
				.executeQuery("SELECT tc.first_name, tc.last_name, tc.mobile_number from tr_customer tc;");

		while (rs.next()) {
			System.out.println(
					rs.getString("first_name") + " " + rs.getString("last_name") + " " + rs.getString("mobile_number"));
		}

	}

}
