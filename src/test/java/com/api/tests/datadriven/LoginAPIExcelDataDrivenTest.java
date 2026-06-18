package com.api.tests.datadriven;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;


public class LoginAPIExcelDataDrivenTest {
	
@Test (description = "Verifying if login API is working for FD user", 
			groups = {"api", "regression", "datadriven"}, 
			dataProviderClass = com.dataproviders.DataProviderUtils.class,  // Fully Qualified name
			dataProvider = "LoginAPIExcelDataProvider"  
			)
	
	
	
	public void loginAPITest(UserCredentials userCredentials ){
	   
	    given()
	        .spec(requestSpec(userCredentials))
	    .when()
	        .post("/login") // ensure correct endpoint path
	    .then()
	       .spec(responseSpec_OK())
	        .body("message", equalTo("Success"))
	        .body(matchesJsonSchemaInClasspath(
	            "response-schema/loginResponseSchema.json"
	        ));
	}

	
}

