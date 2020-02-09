package com.schoology.test.autocomplete.core.entity;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(!(obj instanceof User)) {
            return false;
        }

        User other = (User) obj;
        return Objects.equals(this.name, other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.name);
    }
}
