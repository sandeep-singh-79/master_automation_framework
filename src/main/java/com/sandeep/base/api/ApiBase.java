package com.sandeep.base.api;

import io.restassured.authentication.BasicAuthScheme;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

import static io.restassured.RestAssured.given;

@Slf4j
public class ApiBase {
    private RequestSpecification requestSpecification;
    private RequestSpecBuilder specBuilder;

    public ApiBase(String baseURI, byte port, String basePath) {
        specBuilder = new RequestSpecBuilder().setBaseUri(baseURI).setPort(port).setBasePath(basePath);
    }

    public ApiBase set_preemptive_basic_auth(final String user, final String password) {
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName(user);
        authScheme.setPassword(password);
        specBuilder.setAuth(authScheme);

        return this;
    }

    public ApiBase set_basic_auth(final String user, final String password) {
        BasicAuthScheme authScheme = new BasicAuthScheme();
        authScheme.setUserName(user);
        authScheme.setPassword(password);
        specBuilder.setAuth(authScheme);

        return this;
    }

    public ApiBase set_session_config(final String session_id) {
        specBuilder.setSessionId(session_id);

        return this;
    }

    public RequestSpecification build_request_spec() {
        requestSpecification = specBuilder.build().given();
        return requestSpecification;
    }

    public Response get_response(final Method method, final EndPoints end_point) throws NullPointerException {
        Response response = null;
        if (requestSpecification == null && specBuilder != null) {
            requestSpecification = build_request_spec();
        }

        if("GET".equals(method.toString()))
            response = given().spec(requestSpecification).when().get(end_point.toString());

        return response;
    }
}
