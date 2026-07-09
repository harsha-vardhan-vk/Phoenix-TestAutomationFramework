package com.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JobHeadModel {
    private int mst_Oem_id;
    private int mst_service_location_id;
    private int mst_warranty_status_id;
    private int mst_platform_id;
}