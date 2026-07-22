package com.api.utils;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.api.utils.ConfigManager.*;

import org.hamcrest.Matchers;

import com.api.constant.Role;
import com.api.filters.SensitiveDataFilter;
import com.api.request.model.UserCredentials;

public class SpecUtil { // Method overloading happening
//static method
	
	@Step("Seting up the BaseURI, Content Type as Application/JSON and attaching the SensitivedData Filter")
	public static RequestSpecification requestSpec() {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
				.setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON)
				.setAccept(ContentType.JSON)
				.addFilter(new SensitiveDataFilter())
				.build();
		
	return requestSpecification;
	}
	
	@Step("Seting up the BaseURI, Content Type as Application/JSON and attaching the SensitivedData Filter")
	public static RequestSpecification requestSpec(Object payload) {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
				.setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON)
				.setAccept(ContentType.JSON)
				.setBody(payload)
				.addFilter(new SensitiveDataFilter())
				.build();
		
	return requestSpecification;
}
	@Step("Seting up the BaseURI, Content Type as Application/JSON and attaching the SensitivedData Filter for a role")
	public static RequestSpecification requestSpecWithAuth(Role role) {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
				.setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON)
				.setAccept(ContentType.JSON)
				.addHeader("Authorization", AuthTokenProvider.getToken(role))
				.addFilter(new SensitiveDataFilter())
				.build();
		
	return requestSpecification;
	}
	
	@Step("Seting up the BaseURI, Content Type as Application/JSON and attaching the SensitivedData Filter for a role and attaching payload")
	public static RequestSpecification requestSpecWithAuth(Role role, Object Payload) {
		RequestSpecification requestSpecification = new RequestSpecBuilder()
				.setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON)
				.setAccept(ContentType.JSON)
				.addHeader("Authorization", AuthTokenProvider.getToken(role))
				.addFilter(new SensitiveDataFilter())
				.build();
		
	return requestSpecification;
	}
	
	@Step("Expecting the response to have Content Type as Application/JSON, Status 200 and Response Time Less than 1000ms")
	public static ResponseSpecification responseSpec_OK() {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder()
		.expectContentType(ContentType.JSON)
		.expectStatusCode(200)
		.expectResponseTime(Matchers.lessThan(1000L))
		.build();
		
		return responseSpecification;
	}
	
	@Step("Expecting the response to have Content Type as Application/JSON and Response Time Less than 1000ms and status code")
	public static ResponseSpecification responseSpec_JSON(int statusCode) {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder()
		.expectContentType(ContentType.JSON)
		.expectStatusCode(statusCode)
		.expectResponseTime(Matchers.lessThan(1000L))
		.build();
		
		return responseSpecification;
	}
	
	@Step("Expecting the response to have Content Type as Text and Response Time Less than 1000ms and status code")
	public static ResponseSpecification responseSpec_TEXT(int statusCode) {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder()
		.expectStatusCode(statusCode)
		.expectResponseTime(Matchers.lessThan(1000L))
		.build();
		
		return responseSpecification;
	}
	
}