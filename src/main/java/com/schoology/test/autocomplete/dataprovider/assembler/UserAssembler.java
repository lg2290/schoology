package com.schoology.test.autocomplete.dataprovider.assembler;

import com.schoology.test.autocomplete.core.entity.User;
import com.schoology.test.autocomplete.dataprovider.entity.UserDbEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class UserAssembler {
    private static final User NULL_USER = null;

    private UserAssembler() {}

    public static final User from(UserDbEntity userDbEntity) {
        if(Objects.isNull(userDbEntity)) {
            return NULL_USER;
        }

        return User.of(userDbEntity.getName());
    }

    public static final List<User> from(List<UserDbEntity> userDbEntities) {
        if(Objects.isNull(userDbEntities) || userDbEntities.isEmpty()) {
            return Collections.emptyList();
        }

        return userDbEntities
                .stream()
                .map(UserAssembler::from)
                .collect(Collectors.toList());
    }
}
