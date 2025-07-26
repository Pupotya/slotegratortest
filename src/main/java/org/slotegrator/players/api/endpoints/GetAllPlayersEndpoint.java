package org.slotegrator.players.api.endpoints;

import io.restassured.http.ContentType;
import org.slotegrator.core.api.AbstractEndpoint;
import org.slotegrator.players.api.models.GetOnePlayerResponseBody;

import static org.slotegrator.core.configuration.ConfigInitialization.getSiteConfig;

public class GetAllPlayersEndpoint extends AbstractEndpoint {

    public static final String ENDPOINT = "/api/automationTask/getAll";

    public GetAllPlayersEndpoint(String token) {
        api().url(getSiteConfig().baseUrl() + ENDPOINT);
        api().apiRequest()
                .bearerAuthorization(token)
                .contentType(ContentType.JSON);
    }

    public GetOnePlayerResponseBody[] get() {
        return api().get()
                .apiResponse()
                .asClass(GetOnePlayerResponseBody[].class);
    }

}
