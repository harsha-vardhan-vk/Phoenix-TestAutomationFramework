package com.api.utils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constant.Role;
import com.api.request.model.UserCredentials;
import com.api.services.DashboardService;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;

public class AuthTokenProvider {

	private static Map<Role, String> tokenCache = new ConcurrentHashMap<Role, String>();
	private static final Logger LOGGER = LogManager.getLogger(AuthTokenProvider.class);
	
	private AuthTokenProvider() {
	}

	@Step("Getting the Auth token for the role")
	public static String getToken(Role role) {

		LOGGER.info("Checking if the token for {} is present in the cache", role);
		if(tokenCache.containsKey(role)) {
			LOGGER.info("Token found for {}", role);
			return tokenCache.get(role);
		}
		
		LOGGER.info("Token not found making the login request for the role {}", role);
		UserCredentials userCredentials = null;

		if (role == Role.FD) {
			userCredentials = new UserCredentials("iamfd", "password");
		} else if (role == Role.SUP) {
			userCredentials = new UserCredentials("iamsup", "password");
		} else if (role == Role.ENG) {
			userCredentials = new UserCredentials("iameng", "password");
		} else if (role == Role.QC) {
			userCredentials = new UserCredentials("iamqc", "password");
		}

		if (userCredentials == null) {
			throw new IllegalArgumentException("Invalid role: " + role);
		}

		String token =
				given()
					.baseUri(ConfigManager.getProperty("BASE_URI"))
					.contentType(ContentType.JSON)
					.accept(ContentType.JSON)
					.body(userCredentials)
				.when()
					.post("/login")
				.then()
					.log().ifValidationFails()
					.statusCode(200)
					.body("message", equalTo("Success"))
					.extract()
					.jsonPath()
					.getString("data.token");

		LOGGER.info("Token cached for the future request");
		tokenCache.put(role, token);
		return token;
	}
}