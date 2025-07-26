package org.slotegrator.players.api.endpoints;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import org.slotegrator.core.api.AbstractEndpoint;
import org.slotegrator.players.api.models.GetOnePlayerRequestPayload;
import org.slotegrator.players.api.models.GetOnePlayerResponseBody;

import static org.slotegrator.core.configuration.ConfigInitialization.getSiteConfig;

public class GetOnePlayerEndpoint extends AbstractEndpoint {

    public static final String ENDPOINT = "/api/automationTask/getOne";

    public GetOnePlayerEndpoint(String token) {
        api().url(getSiteConfig().baseUrl() + ENDPOINT);
        api().apiRequest()
                .bearerAuthorization(token)
                .contentType(ContentType.JSON);
    }

    public GetOnePlayerResponseBody post(String email) {
        var payload = new GetOnePlayerRequestPayload(email);
        var jsonBody = new Gson().toJson(payload);
        api().apiRequest()
                .body(jsonBody);

        return api().post()
                .apiResponse()
                .asClass(GetOnePlayerResponseBody.class);
    }
}
