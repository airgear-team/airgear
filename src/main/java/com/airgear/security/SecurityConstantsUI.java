package com.airgear.security;

/**
 * The {@code GoodsResponse} class holds constants related to security for the UI module.
 * It provides a standardized way to access security-related constants,
 * such as authorization claims and third-party service identifiers.
 *
 * <p>The class is designed to be non-instantiable, and all its members are static.</p>
 *
 * @author Oleksandr Tuleninov
 * @version 1.0
 */
public final class SecurityConstantsUI {

    private SecurityConstantsUI() {
        throw new AssertionError("non-instantiable class");
    }

    public static final String THIRD_PARTY_SERVICE = "Service";

}
