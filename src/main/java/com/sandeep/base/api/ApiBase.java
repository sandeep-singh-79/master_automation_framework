package com.sandeep.base.api;

import io.restassured.authentication.AuthenticationScheme;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.authentication.OAuth2Scheme;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

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
        set_auth_scheme(authScheme);

        return this;
    }

    private ApiBase set_auth_scheme (AuthenticationScheme authScheme) {
        specBuilder.setAuth(authScheme);
        return this;
    }

    public ApiBase set_basic_auth(final String user, final String password) {
        BasicAuthScheme authScheme = new BasicAuthScheme();
        authScheme.setUserName(user);
        authScheme.setPassword(password);
        set_auth_scheme(authScheme);

        return this;
    }

    public ApiBase set_oauth2_token_and_json_body (final String token, final String json_body) {
        OAuth2Scheme authScheme = new OAuth2Scheme();
        authScheme.setAccessToken(token);
        return set_auth_scheme(authScheme).set_content_type(ContentType.JSON).set_body(json_body);
    }

    public ApiBase set_content_type(final ContentType content_type) {
        specBuilder.setContentType(content_type.toString());
        return this;
    }

    public ApiBase set_body(final String body) {
        specBuilder.setBody(body);
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

        switch (method.toString()) {
            case "GET":
                response = requestSpecification.when().get(end_point.toString());
                break;
            case "POST":
                //
                response = requestSpecification.when().post(end_point.toString());
                break;
        }

        return response;
    }
}
