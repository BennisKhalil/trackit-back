package com.trackit.Device;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(DeviceController.class)
public class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DeviceService deviceServiceImpl;

    @Test
    public void ShouldReturnDeviceWhenCalling_getDeviceByDeviceId() throws Exception {

        when(deviceServiceImpl.findDeviceById("100")).thenReturn(DeviceDto.builder().deviceId("100")
                .lat(Double.valueOf(3)).lon(Double.valueOf(5)).build());
        mockMvc.perform(get("/device/{deviceId}","100"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deviceId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lat").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lon").exists());


    }


}
