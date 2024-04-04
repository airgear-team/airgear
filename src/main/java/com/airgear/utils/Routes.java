package com.airgear.utils;

public class Routes {

    private Routes() {
        throw new AssertionError("Non-instantiable class");
    }

    public static final String AUTH = "/auth";
    public static final String SEARCH = "/search";
    public static final String GOODS = "/goods";
    public static final String MESSAGE = GOODS + "/message";
    public static final String LOCATION = "/location";

}
