package com.schoology.test.autocomplete.unit.core.usecase;

import com.schoology.test.autocomplete.core.entity.User;
import com.schoology.test.autocomplete.core.usecase.argument.GetNamesArgument;
import com.schoology.test.autocomplete.core.usecase.result.GetNameResult;
import com.schoology.test.autocomplete.core.gateway.GetUsersGateway;
import com.schoology.test.autocomplete.core.usecase.GetNamesUseCase;
import com.schoology.test.autocomplete.core.usecase.UseCase;
import com.schoology.test.autocomplete.helper.TestConstants;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@ExtendWith(MockitoExtension.class)
@Tag(TestConstants.TEST_TAG_UNIT)
public class GetNamesUseCaseTest {

    private static final String COMMON_TEXT = "GH";

    private static final String TEXT_WITH_SPACE_BETWEEN = "B CD";

    private static final String CASE_INSENSITIVE_SEARCH = "ef";

    private static final String NAME_1 = "A" + TEXT_WITH_SPACE_BETWEEN + "eF " + COMMON_TEXT;
    private static final String NAME_2 = "ABcdEf" + COMMON_TEXT;
    private static final String NAME_3 = COMMON_TEXT + "iJKLM";

    private static final List<User> ALL_USERS = Stream.of(
            User.of(NAME_1),
            User.of(NAME_2),
            User.of(NAME_3)
        ).collect(Collectors.toList());

    private static final List<String> ALL_NAMES = Stream.of(NAME_1, NAME_2, NAME_3).collect(Collectors.toList());

    @Mock
    private GetUsersGateway getUsersGateway;

    private UseCase<GetNameResult, GetNamesArgument> useCase;

    @BeforeEach
    public void setup() {
        useCase = new GetNamesUseCase(getUsersGateway);
    }

    @Test
    public void whenUserListNull_thenEmptyNameList() {
        Mockito.when(getUsersGateway.get())
                .thenReturn(null);

        assertNamesListEmpty();
    }

    @Test
    public void whenUserListIsEmpty_thenEmptyNameList() {
        Mockito.when(getUsersGateway.get())
                .thenReturn(Collections.emptyList());

        assertNamesListEmpty();
    }

    @Test
    public void whenArgumentIsNull_thenAllTheNames() {
        Mockito.when(getUsersGateway.get())
                .thenReturn(ALL_USERS);

        assertNamesReturned(null, ALL_NAMES);
    }

    @Test
    public void whenNameFilterIsNull_thenAllTheNames() {
        Mockito.when(getUsersGateway.get())
                .thenReturn(ALL_USERS);

        GetNamesArgument argument = GetNamesArgument.of(null);
        assertNamesReturned(argument, ALL_NAMES);
    }

    @Test
    public void whenNameFilterIsEmpty_thenAllTheNames() {
        Mockito.when(getUsersGateway.get())
                .thenReturn(ALL_USERS);

        GetNamesArgument argument = GetNamesArgument.of("");
        assertNamesReturned(argument, ALL_NAMES);
    }

    @Test
    public void whenNameFilterIsBlankSpace_thenAllTheNames() {
        Mockito.when(getUsersGateway.get())
                .thenReturn(ALL_USERS);

        GetNamesArgument argument = GetNamesArgument.of(" ");
        assertNamesReturned(argument, ALL_NAMES);
    }

    @Test
    public void whenNameFilterIsInAllNames_thenAllTheNames() {
        Mockito.when(getUsersGateway.get())
                .thenReturn(ALL_USERS);

        GetNamesArgument argument = GetNamesArgument.of(COMMON_TEXT);
        assertNamesReturned(argument, ALL_NAMES);
    }

    @Test
    public void whenNameFilterStartsAndEndsWithSpace_thenFilterByTrimmedString() {
        List<String> expectedNames = Stream.of(NAME_2).collect(Collectors.toList());

        Mockito.when(getUsersGateway.get())
                .thenReturn(ALL_USERS);

        GetNamesArgument argument = GetNamesArgument.of(" " + NAME_2 + " ");
        assertNamesReturned(argument, expectedNames);
    }

    @Test
    public void whenNameFilterHasSpaceBetweenOtherCharacters_thenFilterByTheExactlyString() {
        List<String> expectedNames = Stream.of(NAME_1).collect(Collectors.toList());

        Mockito.when(getUsersGateway.get())
                .thenReturn(ALL_USERS);

        GetNamesArgument argument = GetNamesArgument.of(TEXT_WITH_SPACE_BETWEEN);
        assertNamesReturned(argument, expectedNames);
    }

    @Test
    public void whenNameFilterHasDifferentCase_thenFilterCaseInsensitive() {
        List<String> expectedNames = Stream.of(NAME_1, NAME_2).collect(Collectors.toList());

        Mockito.when(getUsersGateway.get())
                .thenReturn(ALL_USERS);

        GetNamesArgument argument = GetNamesArgument.of(CASE_INSENSITIVE_SEARCH);
        assertNamesReturned(argument, expectedNames);
    }

    private void assertNamesListEmpty() {
        GetNamesArgument argument = GetNamesArgument.of(RandomString.make());

        assertNamesListEmpty(argument);
    }

    private void assertNamesListEmpty(GetNamesArgument argument) {
        GetNameResult result = useCase.execute(argument);

        Assertions.assertAll("Names list empty",
                () -> Assertions.assertNotNull(result),
                () -> Assertions.assertNotNull(result.getNames()),
                () -> Assertions.assertTrue(result.getNames().isEmpty())
        );

        Mockito.verify(getUsersGateway).get();
    }

    private void assertNamesReturned(GetNamesArgument argument, List<String> expectedNames) {
        GetNameResult result = useCase.execute(argument);

        Assertions.assertAll("Names returned match expected",
                () -> Assertions.assertNotNull(result),
                () -> Assertions.assertNotNull(result.getNames()),
                () -> Assertions.assertFalse(result.getNames().isEmpty()),
                () -> Assertions.assertEquals(expectedNames, result.getNames())
        );

        Mockito.verify(getUsersGateway).get();
    }
}