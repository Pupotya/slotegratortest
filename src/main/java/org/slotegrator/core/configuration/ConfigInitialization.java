package org.slotegrator.core.configuration;

import org.aeonbits.owner.ConfigFactory;

public class ConfigInitialization {

    private ConfigInitialization() {
    }

    private static final SiteConfig SITE_CONFIG;

    static {
        System.setProperty("file.encoding", "UTF8");
        SITE_CONFIG = ConfigFactory.create(SiteConfig.class, System.getProperties());
    };

    public static SiteConfig getSiteConfig() {
        return SITE_CONFIG;
    }

}

