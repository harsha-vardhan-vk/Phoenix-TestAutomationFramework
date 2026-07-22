package com.api.tests;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static com.api.utils.SpecUtil.responseSpec_TEXT;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.services.DashboardService;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Listeners(com.listners.APITestListener.class)
@Epic("Job Management")
@Feature("Job Count")
public class CountAPITest {
	
	private DashboardService dashboardService;
	
	@BeforeMethod(description = "Setting up the DashboardService instance")	
	public void setup() {
	
		dashboardService = new DashboardService();
	}
	

	@Story("Job Count Data is shown correctly")
	@Description("Verifying if count API is giving response")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Verifying if count API is giving reponse", groups = {"api", "regression", "smoke"})
	public void verifyCountAPIResponse() {
		dashboardService.count(Role.FD)
		.then()
			.spec(responseSpec_OK())
			.body("message", equalTo("Success"))
			.body("data", notNullValue())
			.body("data.size()", equalTo(3))
			.body("data.count", everyItem(greaterThanOrEqualTo(0)))
			.body("data.label", everyItem(not(blankOrNullString())))
		.body("data.key", containsInAnyOrder("pending_fst_assignment", "pending_for_delivery", "created_today"))
		//Schema validation
		    .body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema-FD.json"));
		
	}
	
	//Negative scenarios
	@Test(description = "Verifying if count API is giving correct status code for Invalid token", groups = {"api","negative", "regression", "smoke"})
	public void countAPITest_MissingAuthToken() {
	   dashboardService.countWithNoAuth()
	    .then()
	        .spec(responseSpec_TEXT(401));
	}
	}
