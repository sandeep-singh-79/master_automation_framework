package com.sandeep.api_tests;

import com.sandeep.base.BaseAPITest;
import com.sandeep.base.api.EndPoints;
import io.restassured.http.Method;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

@Slf4j
public class APIDemoTest extends BaseAPITest {
    private Response response;

    @BeforeMethod
    public void setupAPITest() {
        try {
            response = apiBase.get_response(Method.GET, EndPoints.USERS).andReturn();
        } catch (NullPointerException e) {
            log.error("Unable to initialize Response object as null was returned!");
        }
    }

    @Test
    public void verifyForFirstNameInResponse() {
        response.then().statusCode(200);
        assertTrue(response.getBody().jsonPath().getList("data.first_name").contains("George"));
    }
}
