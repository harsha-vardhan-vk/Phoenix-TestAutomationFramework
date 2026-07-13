package com.api.tests;

import static org.hamcrest.Matchers.*;
import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.services.AuthService;

import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;


public class LoginAPITest {
	
private AuthService authService;

@BeforeMethod(description = "Create the payload for the Login API")
	public void setup() {
		new UserCredentials("iamfd", "password");

	}
	
	@Test (description = "Verifying if login API is working for FD user", groups = {"api", "regression", "smoke"})
	public void loginAPITest() throws IOException {
	    // Ensure UserCredentials has proper fields + getters/setters
	    UserCredentials userCredentials = new UserCredentials("iamfd", "password");


	    authService.login(userCredentials)
	    .then()
	       .spec(responseSpec_OK())
	        .body("message", equalTo("Success"))
	        .body(matchesJsonSchemaInClasspath(
	            "response-schema/loginResponseSchema.json"
	        ));
	}

	
}

