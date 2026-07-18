package com.api.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class SensitiveDataFilter implements Filter {

	@Override
	public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
			FilterContext ctx) {
		System.out.println("---------------------HELLO From the Filter!!---------------------");
		Response response = ctx.next(requestSpec, responseSpec); // make the request!!
		System.out.println("--------------------- I got the Response in Filter!!---------------------");
		return response;
	}

	
}
