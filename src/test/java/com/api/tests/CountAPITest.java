package com.api.tests;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import static com.api.constant.Role.*;
import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static io.restassured.RestAssured.*;

public class CountAPITest {

	@Test
	public void verifyCountAPIResponse() {
		given()
			.baseUri(getProperty("BASE_URI"))
			.header("Authorization", getToken(FD))
			.log().uri()
			.log().method()
			.log().headers()
		.when()
			.get("/dashboard/count")
		.then()
			.log().all()
			.statusCode(200)
			.body("message", equalTo("Success"))
			.time(lessThan(1000L))
			.body("data", notNullValue())
			.body("data.size()", equalTo(3))
			.body("data.count", everyItem(greaterThanOrEqualTo(0)))
			.body("data.label", everyItem(not(blankOrNullString())))
		.body("data.key", containsInAnyOrder("pending_fst_assignment", "pending_for_delivery", "created_today"))
		//Schema validation
		    .body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema-FD.json"));
		
	}
	
	//Negative scenarios 
	@Test
	public void countAPITest_MissingAuthToken() {
	    given()
	        .baseUri(getProperty("BASE_URI"))
	        .and()
	        .log().uri()
			.log().method()
			.log().headers()
	    .when()
	        .get("/dashboard/count")
	    .then()
	        .log().all()
	        .statusCode(401);
	}
	}
