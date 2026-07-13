package com.api.services;

import static com.api.utils.SpecUtil.requestSpec;
import static io.restassured.RestAssured.given;

import com.api.request.model.UserCredentials;

import io.restassured.response.Response;

public class AuthService {

	// Service class purpose is going to hole the APIs that belongs to Auth
	
	private static final String LOGIN_ENDPOINT = "/login";
	
	public Response login(Object userCredentials) {

		Response response = given()
        .spec(requestSpec(userCredentials))
        .header("Dummy", "123")
    .when()
        .post(LOGIN_ENDPOINT); 
	    
	    return response;
	}
}
