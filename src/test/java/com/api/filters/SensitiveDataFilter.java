package com.api.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class SensitiveDataFilter implements Filter {
	private static final Logger LOGGER = LogManager.getLogger(SensitiveDataFilter.class);
	
	@Override
	public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
			FilterContext ctx) {
		System.out.println("---------------------HELLO From the Filter!!---------------------");
		redactPayload(requestSpec);
		Response response = ctx.next(requestSpec, responseSpec); // make the request!!
		System.out.println("--------------------- I got the Response in Filter!!---------------------");
		
		redactResponseBody(response);
		
		return response;
	}
	
	private void redactResponseBody(Response response) {
		String responseBody = response.asPrettyString();
		responseBody = responseBody.replaceAll("\"token\"\s*:\s*\"[^\"]+\"", "\"token\": \"[REDACTED]\"");
		LOGGER.info("RESPONSE PAYLOAD : {}", responseBody);
	}

	//Create a method which is going to REDACT / Hide the password from the Request Payload
	
	public void redactPayload(FilterableRequestSpecification requestSpec) {
		String requestPayload = requestSpec.getBody().toString();
		
		//Hide the payload 
		requestPayload = requestPayload.replaceAll("\"password\"\s*:\s*\"[^\"]+\"", "\"password\": \"[REDACTED]\"");
		LOGGER.info("REQUEST PAYLOAD : {}", requestPayload);
	}
	
	
}