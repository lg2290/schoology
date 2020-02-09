package com.schoology.test.autocomplete.core.usecase;

import com.schoology.test.autocomplete.core.entity.User;
import com.schoology.test.autocomplete.core.gateway.GetUsersGateway;
import com.schoology.test.autocomplete.core.usecase.argument.GetNamesArgument;
import com.schoology.test.autocomplete.core.usecase.result.GetNameResult;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetNamesUseCase implements UseCase<GetNameResult, GetNamesArgument> {

    private final GetUsersGateway getUsersGateway;

    public GetNamesUseCase(final GetUsersGateway getUsersGateway) {
        this.getUsersGateway = getUsersGateway;
    }

    @Override
    public GetNameResult execute(GetNamesArgument getNamesArgument) {
        Stream<String> names = getNames();

        Stream<String> filteredNames = filterNames(getNamesArgument, names);

        return GetNameResult.of(filteredNames.collect(Collectors.toList()));
    }

    private Stream<String> getNames() {
        List<User> users = getUsersGateway.get();

        if(noUserFound(users)) {
            return Stream.empty();
        }

        return users
                .stream()
                .map(User::getName);
    }

    private boolean noUserFound(List<User> users) {
        return Objects.isNull(users) || users.isEmpty();
    }

    private Stream<String> filterNames(GetNamesArgument getNamesArgument, Stream<String> names) {
        if(hasFilter(getNamesArgument)) {
            String nameFilter = getNamesArgument.getNameFilter().toLowerCase().trim();

            return names.filter(n ->
                Objects.nonNull(n) &&
                        n.toLowerCase().contains(nameFilter)
            );
        }

        return names;
    }

    private boolean hasFilter(GetNamesArgument getNamesArgument) {
        return Objects.nonNull(getNamesArgument)
                && !StringUtils.isEmpty(getNamesArgument.getNameFilter())
                && !getNamesArgument.getNameFilter().isBlank();
    }
}
