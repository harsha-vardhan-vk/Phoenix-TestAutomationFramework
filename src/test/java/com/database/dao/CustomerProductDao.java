package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.database.DatabaseManagerForHikariCP;
import com.database.model.CustomerProductDBModel;

public class CustomerProductDao {
	
	private static final Logger LOGGER = LogManager.getLogger(CustomerProductDao.class);

	private static final String PRODUCT_QUERY = """
						SELECT * FROM tr_customer_product
			WHERE id = ?

						""";
	
	private CustomerProductDao() {
		
	}
	
	public static CustomerProductDBModel getProductInfoFromDB(int tr_customer_product_id) {
		CustomerProductDBModel customerProductDBModel = null;
		
		try {
			LOGGER.info("Getting the connection from the Database Manager");
			Connection conn = DatabaseManagerForHikariCP.getConnection();
			PreparedStatement ps = conn.prepareStatement(PRODUCT_QUERY);
			ps.setInt(1, tr_customer_product_id);
			LOGGER.info("Executing the SQL Query", PRODUCT_QUERY);
			
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				customerProductDBModel = new CustomerProductDBModel(rs.getInt("id"), 
						rs.getInt("tr_customer_id"), 
						rs.getInt("mst_model_id"),
						rs.getString("dop"),
						rs.getString("popurl"),
						rs.getString("imei2"),
						rs.getString("imei1"), 
						rs.getString("serial_number"));
			}
		} catch (SQLException e) {
			LOGGER.error("Cannot Convert the ResultSet to the CustomerProductDBModel bean", e);
			
		}
		
		return customerProductDBModel;
	}
}
