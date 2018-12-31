package com.mckinsey.cet.api_tests;

import com.mckinsey.cet.base.BaseAPITest;
import com.mckinsey.cet.util.api.EndPoints;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.when;
import static org.testng.Assert.assertTrue;

@Slf4j
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
