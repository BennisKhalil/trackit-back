package com.trackit.device;

import com.trackit.exception.DeviceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
@RunWith(MockitoJUnitRunner.class)
public class DeviceServiceTest {


    @Mock
    private DeviceRepo deviceRepo;

    @InjectMocks
    private DeviceServiceImpl deviceServiceImpl;

    @Before
    public void init(){

    }

    @Test
    public void shouldReturnDeviceWhenFindDeviceByIdIsCalled() throws DeviceNotFoundException {
        Device device = Device.builder()
                .deviceId("1")
                .lat(Double.valueOf(3))
                .lon(Double.valueOf(6))
                .build();
        when(deviceRepo.findByDeviceId("1")).thenReturn(device);

        DeviceDTO deviceDTO = deviceServiceImpl.findDeviceById("1");
        assertEquals(deviceDTO.getDeviceId(),"1");
        assertEquals(deviceDTO.getLat(),Double.valueOf(3));
        assertEquals(deviceDTO.getLon(),Double.valueOf(6));
        verify(deviceRepo, times(1)).findByDeviceId("1");
        verifyNoMoreInteractions(deviceRepo);

    }

    @Test(expected =DeviceNotFoundException.class )
    public void shouldReturnDeviceNotFoundWhenDeviceIdIsNotFound() throws DeviceNotFoundException {

        when(deviceRepo.findByDeviceId("1")).thenReturn(null);

        try {
            deviceServiceImpl.findDeviceById("1");
        } catch (DeviceNotFoundException e) {
            assertEquals("device not found",e.getMessage());
            throw e;
        }

        fail("shouldReturnDeviceNotFoundWhenDeviceIdIsNotFound failed");


    }
}
