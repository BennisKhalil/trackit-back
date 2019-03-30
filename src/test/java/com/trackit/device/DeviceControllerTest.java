package com.trackit.device;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(DeviceController.class)
public class DeviceControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private DeviceService deviceService;


    @Test
    public void  WhenFetchingDeviceWithIdShouldReturnDeviceInAMessage() throws Exception {
        DeviceDTO deviceDTO = DeviceDTO.builder().deviceId("1").lat(3.0).lon(4.0).build();
        when(deviceService.findDeviceById("1")).thenReturn(deviceDTO);
        mvc.perform(MockMvcRequestBuilders
        .get("/device/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deviceId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lat").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lon").exists());
        verify(deviceService, times(1)).findDeviceById("1");
        verifyNoMoreInteractions(deviceService);
    }

}
