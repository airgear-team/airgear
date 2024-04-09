package com.airgear.security;

public final class SecurityConstants {

    private SecurityConstants() {
        throw new AssertionError("non-instantiable class");
    }

    public static final String AUTH_TOKEN_PREFIX = "Bearer ";

    public static final String THIRD_PARTY_SERVICE = "Service";

}
