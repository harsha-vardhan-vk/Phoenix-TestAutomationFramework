package com.api.tests;

import static com.api.utils.SpecUtil.responseSpec_JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.services.UserService;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("User Management")
@Feature("User Details")
@Listeners(com.listners.APITestListener.class)
public class UserDetailsAPITest {

	private UserService userService;
	
@BeforeMethod(description = "Setting up the UserService instance")	
	public void setup() {
	
	userService = new UserService();
	}
	
@Story("userDetails should be shown")
@Description("Verify if the UserDetails API response is shown correctly")
@Severity(SeverityLevel.CRITICAL)

	@Test(description = "Verify if the UserDetails API response is shown correctly", groups = {"api", "smoke", "regression"})
	public void userDetailsAPITest() {
userService.userDetails(Role.FD)
		.then()
			.spec(responseSpec_JSON(200))
			.and()
			.body(matchesJsonSchemaInClasspath(
				"response-schema/userDetailsResponseSchema.json"
			));
	}
}