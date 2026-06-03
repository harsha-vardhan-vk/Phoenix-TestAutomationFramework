package com.api.tests;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.*;

import static com.api.constant.Role.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static io.restassured.RestAssured.*;
public class CountAPITest {
	@Test(description = "Verifying if count API is giving resposne", groups = {"api", "regression", "smoke"})
	public void verifyCountAPIResponse() {
		given()
			.spec(requestSpecWithAuth(FD))
		.when()
			.get("/dashboard/count")
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
	    given()
	        .spec(requestSpec())
	    .when()
	        .get("/dashboard/count")
	    .then()
	        .spec(responseSpec_TEXT(401));
	}
	}
