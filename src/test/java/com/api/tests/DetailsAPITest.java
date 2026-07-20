package com.api.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.Detail;
import com.api.services.DashboardService;
import com.api.utils.SpecUtil;

@Listeners(com.listners.APITestListener.class)
public class DetailsAPITest {

    private DashboardService dashboardService;
    private Detail detailPayload;

    @BeforeMethod(description = "Instantiating the Dashboard service and creating detail payload")
    public void setup() {
        dashboardService = new DashboardService();
        detailPayload = new Detail("created_today");
    }

    @Test(description = "Verify if Details API is working properly",
          groups = {"api", "smoke", "e2e"})
    public void detailAPITest() {
        dashboardService.details(Role.FD, detailPayload)
            .then()
            .spec(SpecUtil.responseSpec_OK())
            .body("message", Matchers.equalTo("Success"));
    }
}
