package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManagerForHikariCP;
import com.database.model.CustomerProductDBModel;

public class CustomerProductDao {

	private static final String PRODUCT_QUERY = """
						SELECT * FROM tr_customer_product
			WHERE id = ?

						""";
	
	private CustomerProductDao() {
		
	}
	
	public static CustomerProductDBModel getProductInfoFromDB(int tr_customer_product_id) {
		CustomerProductDBModel customerProductDBModel = null;
		
		try {
			Connection conn = DatabaseManagerForHikariCP.getConnection();
			PreparedStatement ps = conn.prepareStatement(PRODUCT_QUERY);
			ps.setInt(1, tr_customer_product_id);
			
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
			System.err.println(e.getMessage());
			
		}
		
		return customerProductDBModel;
	}
}
