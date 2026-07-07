package com.database.dao;

import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;

import java.sql.SQLException;

import com.api.constant.Model;
import com.api.constant.Problem;
import com.api.constant.Product;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.database.model.CustomerProductDBModel;

public class DemoDaoRunner {

    public static void main(String[] args) throws SQLException {
        
       CustomerProductDBModel customerProductDBModel= CustomerProductDao.getProductInfoFromDB(345984);
       System.out.println(customerProductDBModel);
       
       CustomerProduct customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "19958695822066", "19958695822066",
				"19958695822066", getTimeWithDaysAgo(10), Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
		
       System.out.println(customerProduct);

    	
    	
    }
}