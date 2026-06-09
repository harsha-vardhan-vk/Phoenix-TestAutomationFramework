package com.api.tests;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Model;
import com.api.constant.OEM;
import com.api.constant.Platform;
import com.api.constant.Problem;
import com.api.constant.Product;
import com.api.constant.Role;
import com.api.constant.ServiceLocation;
import com.api.constant.Warranty_Status;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import static com.api.utils.DateTimeUtil.*;
import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class CreateJobAPITest {

	private CreateJobPayload createJobPayload;
	
	@BeforeMethod(description = "Creating createJob API request payload")
	public void setup() {
	Customer customer = new Customer("Harsha", "vardhana", "9980070086", "", "harsha.vardhan@gmail.com", "");
	CustomerAddress customerAddress = new CustomerAddress("D 185", "SLV Nilaya", "Vaddarahalli", "Kylancha", "Ramanagara", "562159", "India", "Karnataka");
	CustomerProduct customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "19949695824646", "19949695824646", "19949695824646", getTimeWithDaysAgo(10), Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
	Problems problems = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Battery Issue"); 
	
	
	List<Problems> problemList = new ArrayList<Problems>();
	problemList.add(problems);
	
	createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(), Platform.FRONT_DESK.getCode(), Warranty_Status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer, customerAddress, customerProduct, problemList);
	}
	
	@Test(description = "Verifying if create job API is able to create Inwarranty job", groups = {"api", "regression", "smoke"})
	public void createJobAPITest() {
			
		given()
		.spec(requestSpecWithAuth(Role.FD, createJobPayload))
		.when()
		.post("/job/create")
		.then()
	    .spec(responseSpec_OK())
	    .body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
	    .body("message", equalTo("Job created successfully. "))
	    .body("data.mst_service_location_id", equalTo(1))
	    .body("data.job_number", startsWith("JOB_"));
		
		
	}
}
