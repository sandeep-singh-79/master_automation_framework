package ai.leverton.demo.base;

import ai.leverton.demo.config.FrameworkConfig;
import io.restassured.RestAssured;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.Properties;

public abstract class BaseAPITest {
    protected Properties config = FrameworkConfig.getInstance().getConfigProperties();

    @BeforeClass
    public void setup(){
        RestAssured.baseURI = config.getProperty("baseUrl");
        RestAssured.port = Integer.valueOf(config.getProperty("basePort", "80"));
        RestAssured.basePath = config.getProperty("basePath");
    }

    @AfterClass
    public static void cleanup() {
        RestAssured.reset();
    }

}
