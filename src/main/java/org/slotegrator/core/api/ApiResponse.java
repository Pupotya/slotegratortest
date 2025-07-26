package org.slotegrator.core.api;

import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;

public class ApiResponse {

    private final Response response;
    private final ApiTemplate.Config config;
    private final ApiResponseChecker apiResponseChecker;

    public ApiResponse(Response response, ApiTemplate.Config config) {
        this.response = response;
        this.apiResponseChecker= new ApiResponseChecker(this);
        this.config = config;
    }

    public Response getResponse() {
        return response;
    }

    public ApiTemplate.Config getConfig() {
        return config;
    }

    public ApiResponseChecker check() {
        return apiResponseChecker;
    }

    public <T> T asClass(Class<T> cls) {
        return response.as(cls, ObjectMapperType.GSON);
}

}
