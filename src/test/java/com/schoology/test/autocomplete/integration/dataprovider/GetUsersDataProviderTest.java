package com.schoology.test.autocomplete.integration.dataprovider;

import com.schoology.test.autocomplete.core.entity.User;
import com.schoology.test.autocomplete.core.gateway.GetUsersGateway;
import com.schoology.test.autocomplete.dataprovider.GetUsersDataProvider;
import com.schoology.test.autocomplete.dataprovider.repository.UserRepository;
import com.schoology.test.autocomplete.helper.TestConstants;
import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Tag(TestConstants.TEST_TAG_INTEGRATION)
@Sql("/test-data.sql")
public class GetUsersDataProviderTest {

    @Autowired
    private UserRepository userRepository;

    private GetUsersGateway gateway;

    @BeforeEach
    public void setup() {
        gateway = new GetUsersDataProvider(userRepository);
    }

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
