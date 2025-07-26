package org.slotegrator.core.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class ApiRequest<T> {

    private static final Filter allureFilter = new AllureRestAssured();
    private final RequestSpecification requestSpecification;

    public ApiRequest() {
        requestSpecification = defaultRequestSpecification();
    }

    public RequestSpecification defaultRequestSpecification() {
        return new RequestSpecBuilder().setRelaxedHTTPSValidation()
                .build()
                .filter(allureFilter)
                .filter(new SessionFilter());
    }

    public ApiRequest<T> body(String body) {
        requestSpecification.body(body);
        return this;
    }

    public ApiRequest<T> bearerAuthorization(String token) {
        requestSpecification.header("Authorization", "Bearer " + token);
        return this;
    }

    public ApiRequest<T> contentType(ContentType contentType) {
        requestSpecification.contentType(contentType);
        return this;
    }

    public RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }
}
