package org.slotegrator.player.api.models;

import com.google.gson.annotations.SerializedName;

public class GetOnePlayerResponseBody {

    public String id;
    @SerializedName("currency_code")
    public String currencyCode;
    public String username;
    public String email;
    public String name;
    public String surname;

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + "\"," +
                "\"currency_code\":\"" + currencyCode + "\"," +
                "\"username\":\"" + username + "\"," +
                "\"email\":\"" + email + "\"," +
                "\"name\":\"" + name + "\"," +
                "\"surname\":\"" + surname + "\"" +
                "}";
    }

}
