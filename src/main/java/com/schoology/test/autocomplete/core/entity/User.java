package com.schoology.test.autocomplete.core.entity;

public class User {
    private final String name;

    private User(final String name) {
        this.name = name;
    }

    public static final User of(final String name) {
        return new User(name);
    }

    public String getName() {
        return name;
    }

}
