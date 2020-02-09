package com.schoology.test.autocomplete.helper;

import com.schoology.test.autocomplete.core.usecase.result.GetNameResult;
import org.assertj.core.internal.bytebuddy.utility.RandomString;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class TestFixture {
    private TestFixture() {
    }

    public static final GetNameResult getNamesResult() {
        return GetNameResult.of(getNames());
    }

    public static final List<String> getNames() {
        return Stream
                .of(RandomString.make(),
                        RandomString.make(),
                        RandomString.make())
                .collect(Collectors.toList());
    }
}
