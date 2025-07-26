package org.slotegrator.players.api.endpoints;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import org.slotegrator.core.api.AbstractEndpoint;
import org.slotegrator.players.api.PlayerDTO;

import static org.slotegrator.core.configuration.ConfigInitialization.getSiteConfig;

public class DeleteOneEndpoint extends AbstractEndpoint {

    public static final String ENDPOINT = "/api/automationTask/deleteOne/";

    public DeleteOneEndpoint(String token) {
        api().apiRequest()
                .bearerAuthorization(token)
                .contentType(ContentType.JSON);
    }

    @Step("Delete player: ({id})")
    public PlayerDTO delete(String id) {
        api().url(getSiteConfig().baseUrl() + ENDPOINT + id);

        return api().delete()
                .apiResponse()
                .asClass(PlayerDTO.class);
    }
}
