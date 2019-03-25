package com.trackit.Device;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
public class DeviceControllerTest {

    private MockMvc mockMvc;
    @Mock
    private DeviceRepo deviceRepo;
    @InjectMocks
    private DeviceServiceImpl deviceServiceImpl;

    @Test(expected = Exception.class)
    public void ShouldReturnDeviceWhenCalling_getDeviceByDeviceId() throws Exception {
        DeviceDto deviceDto= DeviceDto.builder()
                .deviceId("1").lon("30").lat("6").build();

        when(deviceServiceImpl.findDeviceById("1")).thenReturn(deviceDto);
        mockMvc.perform(get("/device/{deviceId}","1"))
                .andExpect(status().isOk())
               // .andExpect(content().contentType(TestUtil.APPLICATION_JSON-UTF8))
                .andExpect((ResultMatcher) jsonPath("$.DeviceId",is("1")))
                .andExpect((ResultMatcher) jsonPath("$.lat",is("6")))
                .andExpect((ResultMatcher) jsonPath("$.lon",is("30")));

        verify(deviceServiceImpl,times(1)).findDeviceById("1");
        verifyNoMoreInteractions(deviceServiceImpl);
    }


}
