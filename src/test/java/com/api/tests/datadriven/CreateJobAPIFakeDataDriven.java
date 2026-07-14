package com.api.tests.datadriven;

import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;
import com.api.services.JobService;

public class CreateJobAPIFakeDataDriven {
	
private JobService jobService; 
	
	@BeforeMethod(description = "Instantiating the Job Service")
	public void setup() {
	jobService = new JobService();
	
	}
	
	@Test(description = "Verifying if create job API is able to create Inwarranty job", groups = {"api", "regression", "datadriven", "faker"},
	dataProviderClass = com.dataproviders.DataProviderUtils.class,
	dataProvider = "CreateJobAPIFakerDataProvider"
			
			
			
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
