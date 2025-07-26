package org.slotegrator.user;

import org.slotegrator.user.api.LoginEndpoint;
import org.slotegrator.user.api.LoginRequestPayload;
import org.slotegrator.user.api.LoginResponseBody;


import static org.slotegrator.core.configuration.ConfigInitialization.getSiteConfig;

public class TestUser {

    private LoginResponseBody.User user;
    private String accessToken;

    private static class Holder {
        private static final TestUser INSTANCE = new TestUser();
    }

    private TestUser() {
        var response = login();
        this.user = response.user;
        this.accessToken = response.accessToken;
    }

    public static TestUser getInstance() {
        return Holder.INSTANCE;
    }


    private LoginResponseBody login() {
        var payload = new LoginRequestPayload(getSiteConfig().email(), getSiteConfig().password());
        return new LoginEndpoint().post(payload);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public LoginResponseBody.User getUser() {
        return user;
    }

}
