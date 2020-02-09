package com.schoology.test.autocomplete.core.usecase;

public interface UseCase<Result, Argument> {
    Result execute(Argument argument);
}
