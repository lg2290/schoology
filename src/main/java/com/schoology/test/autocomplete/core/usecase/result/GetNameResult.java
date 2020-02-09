package com.schoology.test.autocomplete.core.usecase.result;

import java.util.List;

public class GetNameResult {

    private final List<String> names;

    private GetNameResult(final List<String> names) {
        this.names = names;
    }

    public static final GetNameResult of(final List<String> names) {
        return new GetNameResult(names);
    }

    public List<String> getNames() {
        return names;
    }
}
