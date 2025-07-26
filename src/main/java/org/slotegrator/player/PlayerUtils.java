package org.slotegrator.player;

import org.slotegrator.player.api.models.PlayerDTO;
import org.slotegrator.player.api.models.GetOnePlayerResponseBody;

public class PlayerUtils {

    public static PlayerDTO mapResponseToPlayer(GetOnePlayerResponseBody responseBody) {
        return new PlayerDTO(responseBody.id,
                responseBody.currencyCode,
                responseBody.email, responseBody.name,
                responseBody.surname,
                responseBody.username);
    }
}
