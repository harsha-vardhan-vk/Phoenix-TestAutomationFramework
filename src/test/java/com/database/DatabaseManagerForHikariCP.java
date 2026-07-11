package com.database;

import java.sql.Connection;
import java.sql.SQLException;

import com.api.utils.ConfigManager;
import com.api.utils.EnvUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManagerForHikariCP {

	private static final String DB_URL = EnvUtil.getValue("DB_URL");
	private static final String DB_USER_NAME = EnvUtil.getValue("DB_USER_NAME");
	private static final String DB_PASSWORD = EnvUtil.getValue("DB_USER_PASSWORD");
	private static final int MAXIMUM_POOL_SIZE = Integer.parseInt(ConfigManager.getProperty("MAXIMUM_POOL_SIZE"));
	private static final int MINIMUM_IDLE_COUNT = Integer.parseInt(ConfigManager.getProperty("MINIMUM_IDLE_COUNT"));
	private static final int CONNECTION_TIMEOUT_IN_SECS = Integer
			.parseInt(ConfigManager.getProperty("CONNECTION_TIMEOUT_IN_SECS"));
	private static final int IDLE_TIMEOUT_IN_SECS = Integer.parseInt(ConfigManager.getProperty("IDLE_TIMEOUT_IN_SECS"));
	private static final int MAX_LIFE_TIMEOUT_IN_MINS = Integer.parseInt(ConfigManager.getProperty("MAX_LIFE_TIMEOUT_IN_MINS"));
	private static final String HIKARI_CP_POOL_NAME = ConfigManager.getProperty("HIKARI_CP_POOL_NAME");

	private static HikariConfig hikariConfig;
	private volatile static HikariDataSource hikariDataSource;

	private static Connection conn; // Any update that happens to this conn variable|
	// all threads will be aware of it!!

	private DatabaseManagerForHikariCP() {

	}

	private static void initializePool() {

		if (hikariDataSource == null) { // First Check which all the parallel threads will enter
			synchronized (DatabaseManagerForHikariCP.class) {
				if (hikariDataSource == null) {
					HikariConfig hikariConfig = new HikariConfig();
					hikariConfig.setJdbcUrl(DB_URL);
					hikariConfig.setUsername(DB_USER_NAME);
					hikariConfig.setPassword(DB_PASSWORD);
					hikariConfig.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
					hikariConfig.setMinimumIdle(MINIMUM_IDLE_COUNT);
					hikariConfig.setConnectionTimeout(CONNECTION_TIMEOUT_IN_SECS * 1000); // 10secs
					hikariConfig.setIdleTimeout(IDLE_TIMEOUT_IN_SECS * 1000); // 10secs
					hikariConfig.setMaxLifetime(MAX_LIFE_TIMEOUT_IN_MINS * 60 * 1000); // 30 mins
					hikariConfig.setPoolName(HIKARI_CP_POOL_NAME);
					hikariDataSource = new HikariDataSource(hikariConfig);

				}
			}
		}
	}
	
	public static Connection getConnection() throws SQLException {
		Connection connection = null;
		if(hikariDataSource==null) {
			initializePool(); // Automatic Initialization of HikariDataSource
		}
		
		else if (hikariDataSource.isClosed()) {
			throw new SQLException("HIKARI DATA SOURCE IS CLOSED");
		}
			connection = hikariDataSource.getConnection();
		return connection;
	}
	
}
