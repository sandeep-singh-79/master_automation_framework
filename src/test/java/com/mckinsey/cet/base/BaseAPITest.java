package com.mckinsey.cet.base;

import com.mckinsey.cet.config.FrameworkConfig;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.Properties;

@Slf4j
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
