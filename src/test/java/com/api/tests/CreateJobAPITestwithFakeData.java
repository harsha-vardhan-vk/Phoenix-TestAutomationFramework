package com.api.tests;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.util.Iterator;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;
import com.api.utils.FakerDataGenerator;

public class CreateJobAPITestwithFakeData {
	private CreateJobPayload createJobPayload;

	@BeforeMethod(description = "Creating createJob API request payload")
	public void setup() {
	    Iterator<CreateJobPayload> iterator = 
	        FakerDataGenerator.generateFakeCreateJobData(1);
	    
	    if (iterator.hasNext()) {
	        createJobPayload = iterator.next();
	    }
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
