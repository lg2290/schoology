package com.schoology.test.autocomplete.unit.dataprovider.assembler;

import com.schoology.test.autocomplete.core.entity.User;
import com.schoology.test.autocomplete.dataprovider.assembler.UserAssembler;
import com.schoology.test.autocomplete.dataprovider.entity.UserDbEntity;
import com.schoology.test.autocomplete.helper.TestConstants;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Tag(TestConstants.TEST_TAG_UNIT)
public class UserAssemblerTest {

    @Test
    public void whenUserDbEntityIsNull_thenNullUser() {
        User user = UserAssembler.from((UserDbEntity) null);

        Assertions.assertNull(user);
    }

    @Test
    public void whenUserDbEntityNameIsNull_thenUserNameIsNull() {
        UserDbEntity entity = new UserDbEntity();

        User user = UserAssembler.from(entity);

        Assertions.assertNotNull(user);
        Assertions.assertNull(user.getName());
    }

    @Test
    public void whenUserDbEntityNameNotNull_thenUserNameSameValue() {
        String name = RandomString.make();
        UserDbEntity entity = new UserDbEntity();
        entity.setName(name);

        User user = UserAssembler.from(entity);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(name, user.getName());
    }

    @Test
    public void whenUserDbEntityListIsNull_thenEmptyUserList() {
        List<User> users = UserAssembler.from((List<UserDbEntity>) null);

        Assertions.assertNotNull(users);
        Assertions.assertTrue(users.isEmpty());
    }

    @Test
    public void whenUserDbEntityListIsEmpty_thenEmptyUserList() {
        List<User> users = UserAssembler.from(new ArrayList<>());

        Assertions.assertNotNull(users);
        Assertions.assertTrue(users.isEmpty());
    }

    @Test
    public void whenUserDbEntityListNotEmpty_thenUserListWithOneUserForEachEntity() {
        UserDbEntity entity1 = new UserDbEntity();
        UserDbEntity entity2 = new UserDbEntity();
        List<UserDbEntity> entities = Stream.of(entity1, entity2).collect(Collectors.toList());

        List<User> users = UserAssembler.from(entities);

        Assertions.assertNotNull(users);
        Assertions.assertEquals(entities.size(), users.size());
    }
}
