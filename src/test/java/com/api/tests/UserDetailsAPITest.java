package com.api.tests;

import static com.api.utils.AuthTokenProvider.getToken;
import static com.api.utils.ConfigManager.getProperty;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

import java.io.IOException;

import org.testng.annotations.Test;

import static com.api.constant.Role.*;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;
public class UserDetailsAPITest {

    @Test  
    public void userDetailsAPITest() throws IOException {
        // Use a valid, non-expired JWT token from config
        Header authHeader = new Header("Authorization", getToken(FD));
        given()
            .baseUri(getProperty("BASE_URI"))
            .header(authHeader)
            .accept(ContentType.JSON)
            .log().uri()
            .log().method()
            .log().headers()
        .when()
            .get("/userdetails") // ✅ ensure correct endpoint path
        .then()
            .log().all()
            .statusCode(200)
            .time(lessThan(2000L)) // allow 2s for network variability
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(
                "response-schema/userDetailsResponseSchema.json"
            ));
    }
}

