package com.api.tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static com.api.utils.ConfigManager.*;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.pojo.UserCredentials;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;


public class LoginAPITest {

	@Test
	public void loginAPITest() throws IOException {
	    // Ensure UserCredentials has proper fields + getters/setters
	    UserCredentials userCredentials = new UserCredentials("iamfd", "password");

	    given()
	        .baseUri(getProperty("BASE_URI"))
	        .and()
	        .contentType(ContentType.JSON)
	        .and()
	        .accept(ContentType.JSON)
	        .and()
	        .body(userCredentials)
	        .log().uri()
	        .log().method()
	        .log().headers()
	        .log().body()
	    .when()
	        .post("/login") // ensure correct endpoint path
	    .then()
	        .log().all()
	        .statusCode(200)
	        .time(lessThan(2000L))
	        .body("message", equalTo("Success"))
	        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(
	            "response-schema/loginResponseSchema.json"
	        ));
	}

	
}

