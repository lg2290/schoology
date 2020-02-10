package com.schoology.test.autocomplete.entrypoint.controller.v1;

public final class ApiConstants {
    private ApiConstants() {
    }

    public static final String V1_PATH = "/v1";

    public static final String GET_NAMES_ENDPOINT_PATH = V1_PATH + "/users/names";
    public static final String GET_NAMES_FILTER_PARAMETER_KEY = "nameFilter";
}
