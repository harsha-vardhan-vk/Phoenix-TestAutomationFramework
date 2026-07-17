package com.api.tests;

import static org.hamcrest.Matchers.*;
import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.services.AuthService;

import static com.api.utils.SpecUtil.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class LoginAPITest {
	
private UserCredentials userCredentials;	
private AuthService authService;

@BeforeMethod(description = "Create the payload for the Login API")
	public void setup() {
	userCredentials = new UserCredentials("iamfd", "password");
	authService = new AuthService();

	}
	
	@Test (description = "Verifying if login API is working for FD user", groups = {"api", "regression", "smoke"})
	public void loginAPITest() throws IOException {
	    
	    authService.login(userCredentials)
	    .then()
	       .spec(responseSpec_OK())
	        .body("message", equalTo("Success"))
	        .body(matchesJsonSchemaInClasspath(
	            "response-schema/loginResponseSchema.json"
	        ));
	}

	
}