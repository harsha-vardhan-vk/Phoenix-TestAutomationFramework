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
public class LoginAPIJSONDataDrivenTest {
	
private AuthService authService;
	
	@BeforeMethod(description = "Setting up the Auth Service reference")
	public void setup() {
		authService = new AuthService();
	}
	
@Test (description = "Verifying if login API is working for FD user", 
			groups = {"api", "regression", "datadriven"}, 
			dataProviderClass = com.dataproviders.DataProviderUtils.class,
			dataProvider = "LoginAPIJSONDataProvider"  
			)
	
	
	
	public void loginAPITest(UserBean userCredentials ){
	   
	    authService.login(userCredentials)
	    .then()
	       .spec(responseSpec_OK())
	        .body("message", equalTo("Success"))
	        .body(matchesJsonSchemaInClasspath(
	            "response-schema/loginResponseSchema.json"
	        ));
	}

	
}