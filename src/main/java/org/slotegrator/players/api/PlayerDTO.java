package org.slotegrator.players.api;


import com.google.gson.annotations.SerializedName;

public class PlayerDTO {

    @SerializedName("_id")
    public String id;
    @SerializedName("currency_code")
    public String currencyCode;
    public String email;
    public String name;
    @SerializedName("password_change")
    public String passwordChange;
    @SerializedName("password_repeat")
    public String passwordRepeat;
    public String surname;
    public String username;

    public PlayerDTO() {
    }

    public PlayerDTO(String id, String currencyCode, String email, String name, String surname, String username) {
        this.id = id;
        this.currencyCode = currencyCode;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.username = username;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + "\"," +
                "\"username\":\"" + username + "\"," +
                "\"email\":\"" + email + "\"," +
                "\"name\":\"" + name + "\"," +
                "\"surname\":\"" + surname + "\"" +
                "}";
    }

}
