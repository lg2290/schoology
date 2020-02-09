package com.schoology.test.autocomplete.entrypoint.controller.v1.response;

import java.time.Instant;

public class EntrypointResponse<T> {
    private Instant timestamp;
    private T responseData;

    private EntrypointResponse(T responseData) {
        this.responseData = responseData;
        this.timestamp = Instant.now();
    }

    public static final <T> EntrypointResponse<T> of(T responseData) {
        return new EntrypointResponse<>(responseData);
    }
}
