package com.database.dao;

import java.sql.SQLException;

import com.api.request.model.Customer;
import com.database.model.CustomerDBModel;

public class DemoDaoRunner {

	public static void main(String[] args) throws SQLException {
		CustomerDBModel customerDBData = CustomerDao.getCustomerInfo();
		System.out.println(customerDBData);
		System.out.println(customerDBData.getFirst_name());
		System.out.println(customerDBData.getLast_name());
		System.out.println(customerDBData.getMobile_number());
		System.out.println(customerDBData.getEmail_id());

		Customer customer = new Customer("Mari", "Gowda", "9980070060", "", "marigowda675@gmail.com", "");
		System.out.println(customer);
	}

}
