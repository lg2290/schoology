package com.schoology.test.autocomplete.component;

import com.schoology.test.autocomplete.entrypoint.controller.v1.ApiConstants;
import com.schoology.test.autocomplete.helper.TestConstants;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql("/test-data.sql")
@Tag(TestConstants.TEST_TAG_COMPONENT)
public class GetNamesControllerComponentTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenNameFilterHasValue_thenReturnNamesThatContainValue() throws Exception {
        String nameFilter = "name";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(ApiConstants.GET_NAMES_ENDPOINT_PATH)
                .param(ApiConstants.GET_NAMES_FILTER_PARAMETER_KEY, nameFilter);

        mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseData", Matchers.containsInAnyOrder("Name One", "Second Name")))
                .andReturn();
    }

}
