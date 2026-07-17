package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.database.DatabaseManagerForHikariCP;
import com.database.model.JobHeadModel;

public class JobHeadDao {

	private static final Logger LOGGER = LogManager.getLogger(JobHeadDao.class);
	private static final String JOB_HEAD_QUERY = """
			SELECT * FROM tr_job_head tjh  WHERE tr_customer_id=?
			""";

	private JobHeadDao() {
	}

	public static JobHeadModel getDataFromJobHead(int tr_customer_id) {
		JobHeadModel jobHeadModel = null;
		try {
			LOGGER.info("Getting the connection from the Database Manager");
			Connection conn = DatabaseManagerForHikariCP.getConnection();
			PreparedStatement ps = conn.prepareStatement(JOB_HEAD_QUERY);
			ps.setInt(1, tr_customer_id);
			LOGGER.info("executing the SQL Query", JOB_HEAD_QUERY);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				jobHeadModel = new JobHeadModel(rs.getInt("id"), 
						rs.getString("job_number"), 
						rs.getInt("tr_customer_id"),
						rs.getInt("tr_customer_product_id"),
						rs.getInt("mst_service_location_id"), 
						rs.getInt("mst_platform_id"), 
						rs.getInt("mst_warrenty_status_id"),  
						rs.getInt("mst_oem_id"));
			}
		} catch (SQLException e) {
			LOGGER.error("Cannot Convert the ResultSet to the JobHeadModel bean", e);
			e.printStackTrace();
		}
		return jobHeadModel;
	}
}