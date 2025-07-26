package org.slotegrator.player;

import com.github.javafaker.Faker;
import org.slotegrator.player.api.models.PlayerDTO;

import java.util.Locale;
import java.util.UUID;

public class PlayerGenerator {

    private static final Faker faker = new Faker(Locale.of("en"));

    public static PlayerDTO generatePlayer() {
        PlayerDTO player = new PlayerDTO();

        String uuid = UUID.randomUUID().toString().substring(0, 8);

        player.currencyCode = "USD";
        player.name = faker.name().firstName();
        player.surname = faker.name().lastName();
        player.email = faker.internet().emailAddress();
        player.username = faker.name().username() + "_" + uuid;

        // consistent secure password
        player.passwordChange = "$TestPass123!";
        player.passwordRepeat = "$TestPass123!";

        return player;
    }
}