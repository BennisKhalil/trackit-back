package com.trackit.enterprise;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@WebMvcTest(EnterpriseController.class)
public class EnterpriseControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void WhenFetchingForAllEnterpriseShouldReturnAllEnterprisesInAMessage() throws Exception {
        mvc.perform(MockMvcRequestBuilders
            .get("/enterprises")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.jsonPath("$.enterprises").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.enterprises[*].enterpriseId").isNotEmpty());
    }
}
