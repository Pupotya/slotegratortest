package org.slotegrator.core.api;

import org.slotegrator.core.AssertionUtils;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class ApiResponseChecker {

    private final ApiResponse apiResponse;

    public ApiResponseChecker(ApiResponse apiResponse) {
        this.apiResponse = apiResponse;
    }

    public ApiResponse isStatus(Integer status) {
            AssertionUtils.assertThatEquals(String.format("%1s %2s",
                            apiResponse.getConfig().method,
                            apiResponse.getConfig().url),
                    apiResponse.getResponse().statusCode(), status);

        return apiResponse;
    }

    public ApiResponseChecker isMatchJsonSchema(String schemaPath) {
        apiResponse.getResponse().then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(schemaPath));
        return this;
    }

}
