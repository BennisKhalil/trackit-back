package com.trackit.driver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackit.exception.DriverNotFoundForEnterpriseException;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DriverController.class)
public class DriverControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private DriverService driverService;

    private ObjectMapper objectMapper;

    @Before
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void WhenFetchingForAllDriversWithEnterpriseIdShouldReturnAllDriversInAMessage() throws Exception {
        List<DriverDTO> driverDTOS = new ArrayList<>();
        driverDTOS.add(DriverDTO.builder().id(1).firstName("Jhon").lastName("Smith").employedDate("12/08/2010").birthDay("15/09/2005").build());
        when(driverService.findAllDriversByEnterpriseId(1)).thenReturn(driverDTOS);
        mvc.perform(MockMvcRequestBuilders
                .get("/enterprises/1/drivers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.drivers").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.drivers[*].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.drivers[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.drivers[0].id").value(1));


        verify(driverService, times(1)).findAllDriversByEnterpriseId(1);
        verifyNoMoreInteractions(driverService);
    }

    @Test
    public void WhenFetchingDriverWithIdShouldReturnDriverInAMessage() throws Exception {
        DriverDTO driverDTO = DriverDTO.builder().id(1).firstName("Jhon").lastName("Smith").employedDate("12/08/2010").birthDay("15/09/2005").build();
        when(driverService.getDriverByDriverIdAndEntrepriseId(1,1)).thenReturn(driverDTO);
        mvc.perform(MockMvcRequestBuilders
                .get("/enterprises/1/drivers/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.drivers").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.drivers[*].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.drivers[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.drivers[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.drivers[1]").doesNotExist());

        verify(driverService, times(1)).getDriverByDriverIdAndEntrepriseId(1,1);
        verifyNoMoreInteractions(driverService);
    }

    @Test
    public void WhenAddingdriverShouldReturnTheAddedEntity() throws Exception {
        DriverDTO driver = DriverDTO.builder().id(1).firstName("Jhon").lastName("Smith").employedDate("12/08/2010").birthDay("15/09/2005").build();
        when(driverService.addDriver(any(DriverDTO.class),anyInt())).thenReturn(driver);
        mvc.perform(MockMvcRequestBuilders
                .post("/enterprises/1/drivers")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(driver)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.drivers").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.drivers[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.drivers[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.drivers[1]").doesNotExist());
        verify(driverService, times(1)).addDriver(any(DriverDTO.class),anyInt());
        verifyNoMoreInteractions(driverService);

    }

    @Test
    public void WhenUpdatingdriverShouldReturnTheUpdatedEntity() throws Exception {
        DriverDTO driver = DriverDTO.builder().id(1).firstName("Jhon").lastName("Smith").employedDate("12/08/2010").birthDay("15/09/2005").build();
        when(driverService.updateDriver(any(DriverDTO.class),anyInt())).thenReturn(driver);
        mvc.perform(MockMvcRequestBuilders
                .put("/enterprises/1/drivers/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(driver)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.drivers").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.drivers[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.drivers[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.drivers[1]").doesNotExist());

        verify(driverService, times(1)).updateDriver(any(DriverDTO.class),anyInt());
        verifyNoMoreInteractions(driverService);
    }

    @Test
    public void WhenDeletingdriverShouldDeleteEntity() throws Exception, DriverNotFoundForEnterpriseException {
        DriverDTO driver = DriverDTO.builder().id(1).firstName("Jhon").lastName("Smith").employedDate("12/08/2010").birthDay("15/09/2005").build();
        mvc.perform(MockMvcRequestBuilders
                .delete("/enterprises/1/drivers/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent());

        verify(driverService, times(1)).deleteDriver(1,1);
        verifyNoMoreInteractions(driverService);
    }
}
