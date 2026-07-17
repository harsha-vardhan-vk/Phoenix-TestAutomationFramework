package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.database.DatabaseManagerForHikariCP;
import com.database.model.CustomerAddressDBModel;

public class CustomerAddressDao {

	private static final Logger LOGGER = LogManager.getLogger(CustomerAddressDao.class);
    private static final String CUSTOMER_ADDRESS_QUERY = """
            SELECT id,
            flat_number,
            apartment_name,
            street_name,
            landmark,
            area,
            pincode,
            country,
            state
            FROM tr_customer_address WHERE id = ?
            """;

    private CustomerAddressDao() {
    }

    public static CustomerAddressDBModel getCustomerAddress(int customerAddressId) {
        CustomerAddressDBModel customerAddressDBModel = null;
        try {
        	LOGGER.info("Getting the connection from the Database Manager");
            Connection conn = DatabaseManagerForHikariCP.getConnection();
            LOGGER.info("Execution the SQL Query {}", CUSTOMER_ADDRESS_QUERY);
            PreparedStatement ps = conn.prepareStatement(CUSTOMER_ADDRESS_QUERY);
            ps.setInt(1, customerAddressId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                customerAddressDBModel = new CustomerAddressDBModel(
                        rs.getInt("id"),
                        rs.getString("flat_number"),
                        rs.getString("apartment_name"),
                        rs.getString("street_name"),
                        rs.getString("landmark"),
                        rs.getString("area"),
                        rs.getString("pincode"),
                        rs.getString("country"),
                        rs.getString("state")
                );
            }
        } catch (SQLException e) {
        	LOGGER.error("Cannot covert the result set to the CustomerAddressDBModel bean", e);
            e.printStackTrace();
        }
        return customerAddressDBModel;
    }
}