package org.slotegrator.core.api;

public abstract class AbstractEndpoint {

    private final ApiTemplate api;

    public AbstractEndpoint() {
        api = new ApiTemplate();
    }

    public ApiTemplate api() {
        return api;
    }
}
