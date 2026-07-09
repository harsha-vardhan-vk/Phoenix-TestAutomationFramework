package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManagerForHikariCP;
import com.database.model.JobHeadModel;

public class JobHeadDao {

    private static final String JOB_HEAD_QUERY = """
            SELECT mst_oem_id,
            mst_service_location_id,
            mst_warrenty_status_id,
            mst_platform_id
            FROM tr_job WHERE id = ?
            """;

    private JobHeadDao() {
    }

    public static JobHeadModel getDataFromJobHead(int jobId) {
        JobHeadModel jobHeadModel = null;
        try {
            Connection conn = DatabaseManagerForHikariCP.getConnection();
            PreparedStatement ps = conn.prepareStatement(JOB_HEAD_QUERY);
            ps.setInt(1, jobId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                jobHeadModel = new JobHeadModel(
                        rs.getInt("mst_oem_id"),
                        rs.getInt("mst_service_location_id"),
                        rs.getInt("mst_warrenty_status_id"),
                        rs.getInt("mst_platform_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobHeadModel;
    }
}