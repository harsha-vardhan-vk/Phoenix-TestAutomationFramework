package com.api.tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static com.api.utils.ConfigManager.*;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.utils.SpecUtil;

import io.restassured.http.ContentType;
import static io.restassured.module.jsv.JsonSchemaValidator.*;


public class LoginAPITest {

	@Test
	public void loginAPITest() throws IOException {
	    // Ensure UserCredentials has proper fields + getters/setters
	    UserCredentials userCredentials = new UserCredentials("iamfd", "password");

	    given()
	        .spec(SpecUtil.requestSpec(userCredentials))
	    .when()
	        .post("/login") // ensure correct endpoint path
	    .then()
	       .spec(SpecUtil.responseSpec_OK())
	        .body("message", equalTo("Success"))
	        .body(matchesJsonSchemaInClasspath(
	            "response-schema/loginResponseSchema.json"
	        ));
	}

	
}

