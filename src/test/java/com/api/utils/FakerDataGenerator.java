package com.api.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.github.javafaker.Faker;

public class FakerDataGenerator {

	//Util Class
	private static Faker faker = new Faker (new Locale("eng-IND"));
	private final static String COUNTRY = "India";
	private static Random RANDOM = new Random();
	private final static int MST_SERVICELOCATION_ID =0;
	private final static int MST_PLATFORM_ID =2;
	private final static int MST_WARRANTY_STATUS_ID =1;
	private final static int MST_OEM_ID =1;
	private final static int MST_PRODUCT_ID = 1;
	private final static int MST_MODEL_ID = 1;
	
	private final static int validProblemId[] = {1,2,3,4,5,6,7,8,9,10,11,12,15,15,17,19,20,22,24,26,27,28,29};
	private FakerDataGenerator() {
		
	}
	
	
	public static Iterator<CreateJobPayload> generateFakeCreateJobData(int count) {
	List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();	
	for (int i=1; i<=count; i++) {
	Customer customer = generateFakeCustomerData();
	CustomerAddress customerAddress= generateFakeCustomerAddressData();
	CustomerProduct customerProduct = generateFakeCustomerProduct();
	List<Problems> problemsList= generateFakeProblemsList();
	CreateJobPayload payload = new CreateJobPayload(MST_SERVICELOCATION_ID, MST_PLATFORM_ID, MST_WARRANTY_STATUS_ID, MST_OEM_ID, customer, customerAddress, customerProduct, problemsList);
	payloadList.add(payload);
	}
	
	return payloadList.iterator();
	
}



	private static Customer generateFakeCustomerData() {
		
		String fname = faker.name().firstName();
		String lname = faker.name().lastName();
		String mobileNumber= faker.numerify("812#######");
		String alternateMobileNumber = faker.numerify("70########");
		String customerEmailAddress = faker.internet().emailAddress();
		String AlternateCustomerEmailAddress = faker.internet().emailAddress();
		
		Customer customer = new Customer(fname, 
				lname, 
				mobileNumber, 
				alternateMobileNumber, 
				customerEmailAddress, 
				AlternateCustomerEmailAddress);
	
	return customer;
	}
	
	private static CustomerAddress generateFakeCustomerAddressData() {
		
		String flatNumber = faker.numerify("###");
		String apartmentName= faker.address().streetName();
		String streetName= faker.address().streetName();
		String landmark = faker.address().streetName();
		String area = faker.address().streetName();
		String pincode = faker.numerify("#####");
		String state = faker.address().state();
		
		CustomerAddress customerAddress = new CustomerAddress(flatNumber, 
				apartmentName, 
				streetName, 
				landmark, 
				area, 
				pincode, 
				COUNTRY, // here country object is hardcoded cause due it incorrect name provided by faker 
				state);
		
		return customerAddress;
		
	}
	
	private static CustomerProduct generateFakeCustomerProduct() {
		String dop = DateTimeUtil.getTimeWithDaysAgo(10);
		String imeiSerialNumber = faker.numerify("##############"); //14 digits
		String popUrl = faker.internet().url();
		CustomerProduct customerProduct = new CustomerProduct(dop, 
				imeiSerialNumber, 
				imeiSerialNumber, 
				imeiSerialNumber, 
				popUrl, MST_PRODUCT_ID, MST_MODEL_ID);
		
		return customerProduct;
	}
	

	private static List<Problems> generateFakeProblemsList() {
		int randomIndex = RANDOM.nextInt(validProblemId.length);
		String fakeRemark = faker.lorem().sentence(5);
		Problems problems = new Problems(validProblemId[randomIndex], fakeRemark);
		System.out.println(problems);
		
		List<Problems> problemList= new ArrayList<Problems>();
		problemList.add(problems);
		
		return problemList;
	}
	
	}
	
