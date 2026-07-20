package com.api.tests.datadriven;

import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.services.AuthService;
import com.dataproviders.api.bean.UserBean;

@Listeners(com.listners.APITestListener.class)
public class LoginAPIDataDrivenTest {

	private AuthService authService;

	@BeforeMethod(description = "Initializing the Auth Service")

	public void setup() {
		authService = new AuthService();

	}

	@Test(description = "Verifying if login API is working for FD user", groups = { "api", "regression",
			"datadriven" }, dataProviderClass = com.dataproviders.DataProviderUtils.class, // Fully Qualified name
			dataProvider = "LoginAPIDataProvider")

	public void loginAPITest(UserBean userbean) {

		authService.login(userbean).then().spec(responseSpec_OK())
		.body("message", equalTo("Success"))
		.body(matchesJsonSchemaInClasspath("response-schema/loginResponseSchema.json"));
	}

}
