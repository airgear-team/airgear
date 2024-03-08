package com.airgear.utils;

/**
 * The {@code Routes} class contains the routes for application.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class Routes {

    private Routes() {
        throw new AssertionError("Non-instantiable class");
    }

    public static final String AUTH = "/auth";
    public static final String SEARCH = "/search";
    public static final String GOODS = "/goods";
    public static final String MESSAGE = GOODS + "/message";

}
