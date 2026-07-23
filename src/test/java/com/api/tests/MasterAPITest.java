package com.api.tests;

import static com.api.constant.Role.FD;
import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static com.api.utils.SpecUtil.responseSpec_TEXT;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.services.MasterService;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.module.jsv.JsonSchemaValidator;

@Listeners(com.listners.APITestListener.class)
@Epic("Job Management")
@Feature("Master API")
public class MasterAPITest {
	
	private MasterService masterService;
	
	@BeforeMethod (description = "Instanciating the Master Service Object")
	public void setup() {
		masterService = new MasterService();
		
	}


	@Story("Master API should bring OEM details, problem type, warranty status")
	@Description("Verifying if master API is giving response")
	@Severity(SeverityLevel.BLOCKER)
	
	@Test(description = "Verifying if master API is giving response", groups = {"api", "regression", "smoke"})
	public void masterAPITest() {
		
		masterService.master(FD)
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
