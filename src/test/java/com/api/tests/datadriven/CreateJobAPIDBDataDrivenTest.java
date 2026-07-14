package com.api.tests.datadriven;

import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

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
import com.api.services.JobService;

public class CreateJobAPIDBDataDrivenTest {
	
	private JobService jobService; 
	@BeforeMethod(description = "Instantiating the Job Service")
	public void setup() {
	jobService = new JobService();
	
	}
	
	
	
	@Test(description = "Verifying if create job API is able to create Inwarranty job", groups = {"api", "regression", "datadriven", "csv"},
	dataProviderClass = com.dataproviders.DataProviderUtils.class,
	dataProvider = "CreateJobAPIDBDataProvider"
			
			
			
			)
	public void createJobAPITest(CreateJobPayload createJobPayload) {
			
		jobService.createJob(Role.FD, createJobPayload)
		.then()
	    .spec(responseSpec_OK())
	    .body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
	    .body("message", equalTo("Job created successfully. "))
	    .body("data.mst_service_location_id", equalTo(1))
	    .body("data.job_number", startsWith("JOB_"));
		
		
	}
}
