package org.slotegrator.core.configuration;

import org.aeonbits.owner.Config;


public interface SiteConfig extends Config {

    @Key("base.url")
    @DefaultValue("https://testslotegrator.com")
    String baseUrl();

    @Key("test.email")
    @DefaultValue("${TEST_EMAIL}")
    String email();

    @Key("test.password")
    @DefaultValue("${TEST_PASSWORD}")
    String password();

    @Key("awaitility.pollDelay")
    @DefaultValue("0")
    Integer awaitPollDelay();
    @Key("awaitility.pollInterval")
    @DefaultValue("200")
    Integer awaitPollInterval();
    @Key("awaitility.timeout")
    @DefaultValue("5000")
    Integer awaitTimeout();
}
