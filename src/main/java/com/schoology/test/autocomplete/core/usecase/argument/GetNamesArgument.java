package com.schoology.test.autocomplete.core.usecase.argument;

public class GetNamesArgument {
    private String nameFilter;

    private GetNamesArgument(String nameFilter) {
        this.nameFilter = nameFilter;
    }

    public static GetNamesArgument of(String nameFilter) {
        return new GetNamesArgument(nameFilter);
    }
}
