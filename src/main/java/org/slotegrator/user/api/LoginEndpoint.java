package org.slotegrator.user.api;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import org.slotegrator.core.api.AbstractEndpoint;

import static org.slotegrator.core.configuration.ConfigInitialization.getSiteConfig;

public class LoginEndpoint extends AbstractEndpoint {

    private final static  String ENDPOINT = "/api/tester/login";

    public LoginEndpoint() {
        api().url(getSiteConfig().baseUrl() + ENDPOINT)
                .apiRequest()
                .contentType(ContentType.JSON);
    }

    public LoginResponseBody post(LoginRequestPayload payload) {
        String jsonBody = new Gson().toJson(payload);

        api().apiRequest()
                .body(jsonBody);

        return api().post()
                .apiResponse()
                .check()
                .isStatus(201)
                .asClass(LoginResponseBody.class);
    }

}
