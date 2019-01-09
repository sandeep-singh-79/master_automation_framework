package com.sandeep.base;

import com.sandeep.base.api.ApiBase;
import com.sandeep.config.FrameworkConfig;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.Properties;

@Slf4j
public abstract class BaseAPITest {
    protected Properties config = FrameworkConfig.getInstance().getConfigProperties();
    protected ApiBase apiBase;
    protected RequestSpecification requestSpec;

    @BeforeClass
    public void setup(){
        apiBase = new ApiBase(config.getProperty("baseUrl"), Byte.valueOf(config.getProperty("basePort", "80")),
                config.getProperty("basePath"));
    }

    @AfterClass
    public static void cleanup() {
        RestAssured.reset();
    }

}
