package org.slotegrator.players.api.endpoints;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import org.slotegrator.core.api.AbstractEndpoint;
import org.slotegrator.players.api.PlayerDTO;

import static org.slotegrator.core.configuration.ConfigInitialization.getSiteConfig;

public class CreatePlayerEndpoint extends AbstractEndpoint {

    public static final String ENDPOINT = "/api/automationTask/create";

    public CreatePlayerEndpoint(String token) {
        api().url(getSiteConfig().baseUrl() + ENDPOINT);
        api().apiRequest()
                .bearerAuthorization(token)
                .contentType(ContentType.JSON);
    }

    public PlayerDTO post(PlayerDTO payload) {
        var body = new Gson().toJson(payload);

        api().apiRequest()
                .body(body);
        return api().post()
                .apiResponse()
                .asClass(PlayerDTO.class);
    }
}
