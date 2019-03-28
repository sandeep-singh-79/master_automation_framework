package com.sandeep.base;

import com.sandeep.base.api.ApiBase;
import com.sandeep.config.FrameworkConfig;
import com.sandeep.config.PropertyReader;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.util.Properties;

@Slf4j
public abstract class BaseAPITest {
    protected Properties config = FrameworkConfig.getInstance().getConfigProperties();
    protected ApiBase apiBase;
    protected RequestSpecification requestSpec;
    protected Properties test_data_props = new PropertyReader(new File(System.getProperty("user.dir") + "/src/test/resources/test_data/api_test_data.properties")).getPropertyFile();
    protected Response response;
    protected String base_url;
    protected byte base_port;
    protected Headers headers;

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
