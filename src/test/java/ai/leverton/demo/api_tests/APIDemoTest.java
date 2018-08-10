package ai.leverton.demo.api_tests;

import ai.leverton.demo.base.BaseAPITest;
import ai.leverton.demo.util.api.EndPoints;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.when;

public class APIDemoTest extends BaseAPITest {
    private Response response;

    @BeforeMethod
    public void setupAPITest() {
        response = when().get(EndPoints.v1.toString()).andReturn();

        response.then().statusCode(200);
    }

    @Test
    public void verifyForCountryInResponse() {
        response.getBody().jsonPath().getList("name").contains("Canada");
    }
}
