package com.trackit.car;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackit.enterprise.EnterpriseController;
import com.trackit.enterprise.EnterpriseDTO;
import com.trackit.enterprise.EnterpriseService;
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
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
public class CarControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private CarService carService;

    private ObjectMapper objectMapper;

    @Before
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void WhenFetchingForAllCarsWithEnterpriseIdShouldReturnAllCarsInAMessage() throws Exception {
        List<CarDTO> carDTOS = new ArrayList<>();
        carDTOS.add(CarDTO.builder().id("1").brand("volvo").model("mod").build());
        when(carService.findCarsByEnterpriseId(1)).thenReturn(carDTOS);
        mvc.perform(MockMvcRequestBuilders
                .get("/enterprises/1/cars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cars").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cars[*].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cars[0].id").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cars[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cars[0].brand").value("volvo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cars[0].model").value("mod"));




        verify(carService, times(1)).findCarsByEnterpriseId(1);
        verifyNoMoreInteractions(carService);
    }

    @Test
    public void WhenFetchingCarWithIdAndEnterpriseIdShouldReturnCarInAMessage() throws Exception {
        CarDTO carDTO = CarDTO.builder().id("1").brand("volvo").model("mod").build();
        when(carService.getCarByCarIdAndEntrepriseId(1,"1")).thenReturn(carDTO);
        mvc.perform(MockMvcRequestBuilders
                .get("/enterprises/1/cars/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cars").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cars[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cars[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cars[0].brand").value("volvo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cars[0].model").value("mod"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cars[1]").doesNotExist());

        verify(carService, times(1)).getCarByCarIdAndEntrepriseId(1,"1");
        verifyNoMoreInteractions(carService);
    }
    @Test
    public void WhenAddingCarShouldReturnTheAddedEntity() throws Exception {
        CarDTO car = CarDTO.builder().id("1").brand("volvo").model("mod").build();
        when(carService.addCar(car,1)).thenReturn(car);
        mvc.perform(MockMvcRequestBuilders
                .post("/enterprises/1/cars")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cars").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cars[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cars[0].id").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cars[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cars[0].brand").value("volvo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cars[0].model").value("mod"));

        verify(carService, times(1)).addCar(car,1);
        verifyNoMoreInteractions(carService);

    }

    @Test
    public void WhenUpdatingCarShouldReturnTheUpdatedEntity() throws Exception {
        CarDTO car = CarDTO.builder().id("1").brand("volvo").model("mod").build();
        when(carService.updateCar(car,1)).thenReturn(car);
        mvc.perform(MockMvcRequestBuilders
                .put("/enterprises/1/cars/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cars").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cars[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cars[0].id").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cars[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cars[0].brand").value("volvo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cars[0].model").value("mod"));

        verify(carService, times(1)).updateCar(car,1);
        verifyNoMoreInteractions(carService);
    }

    @Test
    public void WhenDeletingCarShouldDeleteEntity() throws Exception {
        CarDTO car = CarDTO.builder().id("1").brand("volvo").model("mod").build();
        mvc.perform(MockMvcRequestBuilders
                .delete("/enterprises/1/cars/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isNoContent());

        verify(carService, times(1)).deleteCarById("1",1);
        verifyNoMoreInteractions(carService);
    }


}


