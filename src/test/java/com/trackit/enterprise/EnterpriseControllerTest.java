package com.trackit.enterprise;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EnterpriseController.class)
public class EnterpriseControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private EnterpriseService enterpriseService;

    private ObjectMapper objectMapper;

    @Before
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void WhenFetchingForAllEnterpriseShouldReturnAllEnterprisesInAMessage() throws Exception {
        List<EnterpriseDTO> enterprises = new ArrayList<>();
        enterprises.add(EnterpriseDTO.builder().id(1).name("enterprise").address("test").build());
        when(enterpriseService.findAllEnterprise()).thenReturn(enterprises);
        mvc.perform(MockMvcRequestBuilders
                .get("/enterprises"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.enterprises").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.enterprises[*].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.enterprises[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.enterprises[0].id").value(1));

        verify(enterpriseService, times(1)).findAllEnterprise();
        verifyNoMoreInteractions(enterpriseService);
    }

    @Test
    public void WhenFetchingEnterpriseByIdShouldReturnEnterprise() throws Exception {
        when(enterpriseService.findEnterpriseById(1)).thenReturn(EnterpriseDTO.builder()
                                                                            .id(1).name("enterprise").address("test").build());
        mvc.perform(MockMvcRequestBuilders
                .get("/enterprises/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.enterprises").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.enterprises[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.enterprises[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.enterprises[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.enterprises[1]").doesNotExist());

        verify(enterpriseService, times(1)).findEnterpriseById(1);
        verifyNoMoreInteractions(enterpriseService);
    }

    @Test
    public void WhenAddingEnterpriseShouldReturnTheAddedEntity() throws Exception {

        EnterpriseDTO enterprise = EnterpriseDTO.builder().id(1).name("enterprise").address("test").build();

        when(enterpriseService.addEnterprise(any(EnterpriseDTO.class))).thenReturn(enterprise);

        mvc.perform(MockMvcRequestBuilders
                .post("/enterprises")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(enterprise)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.enterprises").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.enterprises[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.enterprises[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.enterprises[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.enterprises[1]").doesNotExist());

        verify(enterpriseService, times(1)).addEnterprise(any(EnterpriseDTO.class));
        verifyNoMoreInteractions(enterpriseService);

    }

    @Test
    public void WhenUpdatingEnterpriseShouldReturnTheUpdatedEntity() throws Exception {
        EnterpriseDTO enterprise = EnterpriseDTO.builder().id(1).name("enterprise").address("test").build();

        when(enterpriseService.updateEnterprise(any(EnterpriseDTO.class))).thenReturn(enterprise);

        mvc.perform(MockMvcRequestBuilders
                .put("/enterprises/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(enterprise)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.enterprises").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.enterprises[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.enterprises[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.enterprises[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.enterprises[1]").doesNotExist());

        verify(enterpriseService, times(1)).updateEnterprise(any(EnterpriseDTO.class));
        verifyNoMoreInteractions(enterpriseService);
    }

    @Test
    public void WhenDeletingEnterpriseShouldDeleteEntity() throws Exception {
        EnterpriseDTO enterprise = EnterpriseDTO.builder().id(1).name("enterprise").address("test").build();

        mvc.perform(MockMvcRequestBuilders
                .delete("/enterprises/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(enterprise)))
                .andExpect(status().isNoContent());

        verify(enterpriseService, times(1)).deleteEnterprise(1);
        verifyNoMoreInteractions(enterpriseService);
    }
}


