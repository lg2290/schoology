package com.schoology.test.autocomplete.integration.entrypoint.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoology.test.autocomplete.core.usecase.UseCase;
import com.schoology.test.autocomplete.core.usecase.argument.GetNamesArgument;
import com.schoology.test.autocomplete.core.usecase.result.GetNameResult;
import com.schoology.test.autocomplete.entrypoint.controller.v1.GetNamesController;
import com.schoology.test.autocomplete.entrypoint.controller.v1.response.EntrypointResponse;
import com.schoology.test.autocomplete.helper.TestConstants;
import com.schoology.test.autocomplete.helper.TestFixture;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@ExtendWith(SpringExtension.class)
@Tag(TestConstants.TEST_TAG_INTEGRATION)
@WebMvcTest(GetNamesController.class)
public class GetNamesControllerTest {

    private static final String URI = "/v1/users/names";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UseCase<GetNameResult, GetNamesArgument> useCase;

    @Captor
    private ArgumentCaptor<GetNamesArgument> getNamesArgumentCaptor = ArgumentCaptor.forClass(GetNamesArgument.class);

    @Test
    public void whenNoFilterIsPassed_thenCallUseCaseAndSuccessReturnNames() throws Exception {
        GetNameResult expectedNames = TestFixture.getNamesResult();

        Mockito
                .when(useCase.execute(Mockito.any(GetNamesArgument.class)))
                .thenReturn(expectedNames);

        mockMvc
                .perform(MockMvcRequestBuilders.get(URI))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseData", Matchers.is(objectMapper.writeValueAsString(expectedNames.getNames()))))
                .andReturn();

        Mockito
                .verify(useCase)
                .execute(getNamesArgumentCaptor.capture());

        GetNamesArgument argument = getNamesArgumentCaptor.getValue();
        Assertions.assertNotNull(argument);
        Assertions.assertNotNull(argument.getNameFilter());
        Assertions.assertTrue(argument.getNameFilter().isEmpty());
    }

    @Test
    public void whenNoFilterIsEmpty_thenCallUseCaseAndSuccessReturnNames() throws Exception {

    }

    @Test
    public void whenNoFilterHasValue_thenCallUseCaseAndSuccessReturnNames() throws Exception {

    }

}