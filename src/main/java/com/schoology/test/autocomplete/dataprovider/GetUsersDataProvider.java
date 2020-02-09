package com.schoology.test.autocomplete.dataprovider;

import com.schoology.test.autocomplete.core.entity.User;
import com.schoology.test.autocomplete.core.gateway.GetUsersGateway;
import com.schoology.test.autocomplete.dataprovider.assembler.UserAssembler;
import com.schoology.test.autocomplete.dataprovider.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetUsersDataProvider implements GetUsersGateway {

    private final UserRepository userRepository;

    public GetUsersDataProvider(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> get() {
        return UserAssembler.from(userRepository.findAll());
    }
}
