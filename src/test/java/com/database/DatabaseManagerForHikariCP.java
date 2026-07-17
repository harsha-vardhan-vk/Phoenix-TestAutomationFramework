package com.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.utils.ConfigManager;
import com.api.utils.EnvUtil;
import com.api.utils.ExcelReaderUtil;
import com.api.utils.VaultDBConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManagerForHikariCP {
	private static final Logger LOGGER = LogManager.getLogger(DatabaseManagerForHikariCP.class);
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

	private static boolean isVaultUp= true;
	private static final String DB_URL = loadSecret("DB_URL");
	private static final String DB_USER_NAME = loadSecret("DB_USER_NAME");
	private static final String DB_PASSWORD = loadSecret("DB_USER_PASSWORD");

	
	public static String loadSecret(String key) {
		String value = null;
		
		if(isVaultUp) {
		value=VaultDBConfig.getSecret(key);
		
		if(value==null) { //When something wrong with Vault! the value becomes null
			System.err.println("Vault is down!! or some issue with Vault");
			LOGGER.error("Vault is Down!! or some issue with Vault");
			isVaultUp = false;
		}
		else {
			System.out.println();
			LOGGER.info("READING VALUE FOR KEY {} FROM VAULT..........", key);
			return value;// coming from Vault!!
			
		}
		}
		LOGGER.info("READING VALUE FROM ENV..........", key);
		value = EnvUtil.getValue(key);
		return value;
	}
		
	private DatabaseManagerForHikariCP() {

	}

	private static void initializePool() {

		if (hikariDataSource == null) { // First Check which all the parallel threads will enter
			LOGGER.warn("Database Connection is not available...... Creating HikariDataSource");
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
					LOGGER.info("Hikari Datasource created!!!");

				}
			}
		}
	}
	
	public static Connection getConnection() throws SQLException {
		Connection connection = null;
		if(hikariDataSource==null) {
			LOGGER.info("Initializing the Database Connection using HikariCP");
			initializePool(); // Automatic Initialization of HikariDataSource
		}
		
		else if (hikariDataSource.isClosed()) {
			LOGGER.error("HIKARI DATA SOURCE IS CLOSED");
			throw new SQLException("HIKARI DATA SOURCE IS CLOSED");
		}
			connection = hikariDataSource.getConnection();
		return connection;
	}
	
}
