package com.api.tests;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.*;

import io.restassured.module.jsv.JsonSchemaValidator;

import static com.api.constant.Role.*;
import static io.restassured.RestAssured.*;

public class MasterAPITest {

	@Test(description = "Verifying if master API is giving resposne", groups = {"api", "regression", "smoke"})
	public void masterAPITest() {
		given()
		.spec(requestSpecWithAuth(FD))
		.when()
		.post("master") // default content-type application/url-formencoded
		.then()
		.spec(responseSpec_OK())
		.body("message", equalTo("Success"))
		.body("data", notNullValue())
		.body("data", hasKey("mst_oem"))
		.body("data", hasKey("mst_model"))
		.body("$", hasKey("message"))
		.body("$", hasKey("data"))
		.body("data.mst_oem.size()", equalTo(2))//check the size of the JSON Array with Matchers
		.body("data.mst_model.size()", greaterThan(0))
		.body("data.mst_oem.id", everyItem(notNullValue()))
		.body("data.mst_oem.name", everyItem(notNullValue()))
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"));	
		
	}
	
	//Negative test
	@Test(description = "Verifying if master API is giving correct status code for invalid token", groups = {"api","negative","regression", "smoke"})
	public void InvalidTokenMasterAPITest() {
	given()
	.spec(requestSpec())
	.log().all()
	.when()
	.post("master") // default content-type application/url-formencoded
	.then()
	.spec(responseSpec_TEXT(401));
}
}
