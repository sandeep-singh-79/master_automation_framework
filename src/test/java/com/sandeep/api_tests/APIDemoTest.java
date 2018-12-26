package com.sandeep.api_tests;

import com.sandeep.base.BaseAPITest;
import com.sandeep.util.api.EndPoints;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.when;
import static org.testng.Assert.assertTrue;

public class APIDemoTest extends BaseAPITest {
    private Response response;

    @BeforeMethod
    public void setupAPITest() {
        response = when().get(EndPoints.v1.toString()).andReturn();

        response.then().statusCode(200);
    }

    @Test
    public void verifyForCountryInResponse() {
        assertTrue(response.getBody().jsonPath().getList("name").contains("Canada"));
    }
}
