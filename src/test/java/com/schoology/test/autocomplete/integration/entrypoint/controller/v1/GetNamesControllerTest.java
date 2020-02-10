package com.schoology.test.autocomplete.integration.entrypoint.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoology.test.autocomplete.core.usecase.UseCase;
import com.schoology.test.autocomplete.core.usecase.argument.GetNamesArgument;
import com.schoology.test.autocomplete.core.usecase.result.GetNameResult;
import com.schoology.test.autocomplete.entrypoint.controller.v1.ApiConstants;
import com.schoology.test.autocomplete.entrypoint.controller.v1.GetNamesController;
import com.schoology.test.autocomplete.helper.TestConstants;
import com.schoology.test.autocomplete.helper.TestFixture;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@Tag(TestConstants.TEST_TAG_INTEGRATION)
@WebMvcTest(GetNamesController.class)
public class GetNamesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UseCase<GetNameResult, GetNamesArgument> useCase;

    @Captor
    private ArgumentCaptor<GetNamesArgument> getNamesArgumentCaptor = ArgumentCaptor.forClass(GetNamesArgument.class);

    @Test
    public void whenNoFilterIsPassed_thenCallUseCaseAndSuccessReturnNames() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder();
        assertSuccessCall(requestBuilder);

        verifyArgument("");
    }

    @Test
    public void whenNameFilterIsEmpty_thenCallUseCaseAndSuccessReturnNames() throws Exception {
        String nameFilter = "";
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(nameFilter);

        assertSuccessCall(requestBuilder);

        verifyArgument(nameFilter);
    }

    @Test
    public void whenNameFilterHasValue_thenCallUseCaseAndSuccessReturnNames() throws Exception {
        String nameFilter = RandomString.make();
        MockHttpServletRequestBuilder requestBuilder = getRequestBuilder(nameFilter);

        assertSuccessCall(requestBuilder);

        verifyArgument(nameFilter);
    }

    private MockHttpServletRequestBuilder getRequestBuilder() {
        return MockMvcRequestBuilders.get(ApiConstants.GET_NAMES_ENDPOINT_PATH);
    }

    private MockHttpServletRequestBuilder getRequestBuilder(String nameFilter) {
        return getRequestBuilder().param(ApiConstants.GET_NAMES_FILTER_PARAMETER_KEY, nameFilter);
    }

    private void assertSuccessCall(MockHttpServletRequestBuilder requestBuilder) throws Exception {
        GetNameResult expectedNames = TestFixture.getNamesResult();

        Mockito
                .when(useCase.execute(Mockito.any(GetNamesArgument.class)))
                .thenReturn(expectedNames);

        mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseData", Matchers.containsInAnyOrder(expectedNames.getNames().toArray())))
                .andReturn();

        Mockito
                .verify(useCase)
                .execute(getNamesArgumentCaptor.capture());
    }

    private void verifyArgument(String expectedArgument) {
        GetNamesArgument argument = getNamesArgumentCaptor.getValue();
        Assertions.assertNotNull(argument);
        Assertions.assertEquals(expectedArgument, argument.getNameFilter());
    }
}
