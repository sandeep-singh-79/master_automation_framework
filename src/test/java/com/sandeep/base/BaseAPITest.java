package com.sandeep.base;

import com.sandeep.base.api.ApiBase;
import com.sandeep.config.FrameworkConfig;
import com.sandeep.config.PropertyFileReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.testng.Assert.fail;

@Slf4j
public abstract class BaseAPITest {
    protected Properties config = FrameworkConfig.getInstance().getConfigProperties();
    protected ApiBase apiBase;
    protected RequestSpecification requestSpec;
    protected Properties test_data_props = new PropertyFileReader(new File(System.getProperty("user.dir") +
            "/src/test/resources/test_data/api_test_data.properties")).getPropertyFile();
    protected Response response;
    protected String base_url;
    protected byte base_port;
    protected Headers headers;
    protected SoftAssert soft_assert = new SoftAssert();

    @BeforeClass
    public void setup () {
        apiBase = new ApiBase(config.getProperty("baseUrl"), Byte.valueOf(config.getProperty("basePort", "80")),
                config.getProperty("basePath"));
    }

    @AfterClass
    public static void cleanup () {
        RestAssured.reset();
    }

    @AfterMethod
    public void log_api_test_result (ITestResult test_result) {
        int method_status = test_result.getStatus();
        String test_status = "";
        switch (method_status) {
            case ITestResult.SUCCESS:
                test_status = "SUCCESS";
                break;
            case ITestResult.FAILURE:
                test_status = "FAILURE";
                break;
            case ITestResult.SKIP:
                test_status = "SKIP";
                break;
        }

        Parameter[] params = test_result.getMethod().getConstructorOrMethod().getMethod().getParameters();
        log.info("Test {} with param(s) {} executed with result: {}", test_result.getMethod().getMethodName(),
                Arrays.stream(params).map(Parameter::getName).collect(Collectors.toList()), test_status);
    }

    protected ApiBase init_api_base (final String base_url,
                                     final byte base_port,
                                     final String end_point,
                                     final Headers headers,
                                     final ContentType contentType) {
        return new ApiBase(base_url, base_port, end_point)
                .set_request_headers(headers)
                .set_content_type(contentType);
    }

    protected void log_exception (Throwable throwable) {
        log.error(throwable.getMessage());
        log.error(String.valueOf(throwable.getCause()));
        stream(ExceptionUtils.getRootCauseStackTrace(throwable)).forEach(log::error);
    }

    protected void log_exception_and_fail (Throwable e) {
        log_exception(e);
        fail(e.getMessage());
    }

    protected List <Map <String, Object>> get_lst_nodes (Response response, String json_path) {
        return response
                .jsonPath()
                .get(json_path);
    }
}
