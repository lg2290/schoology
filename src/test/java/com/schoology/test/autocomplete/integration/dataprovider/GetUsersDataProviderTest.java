package com.schoology.test.autocomplete.integration.dataprovider;

import com.schoology.test.autocomplete.core.entity.User;
import com.schoology.test.autocomplete.core.gateway.GetUsersGateway;
import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@DataJpaTest
public class GetUsersDataProviderTest {

    @Autowired
    private GetUsersGateway gateway;

    @Test
    public void whenGetAllUsers_thenReturnAllUsersInDatabase() {
        List<User> expectedUsers = Stream.of(
                User.of("Name One"),
                User.of("Second Name"),
                User.of("User Number Three")
        ).collect(Collectors.toList());

        List<User> users = gateway.get();

        MatcherAssert.assertThat(users, Matchers.containsInAnyOrder(expectedUsers.toArray()));
    }
}
