package com.trackit.Device;


import com.trackit.exception.DeviceNotFoundException;
import lombok.Builder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class DeviceServiceImplTest {


    @Mock
    private DeviceRepo deviceRepo;

    @InjectMocks
    private DeviceServiceImpl deviceServiceImpl;

    private Device device;
    private DeviceDto deviceDto;

    @Before
    public  void init(){
        device = Device.builder()
                .deviceId("1").lat(Double.valueOf(33)).lon(Double.valueOf(6)).build();
    }


    @Test
    public void shouldReturnDeviceWhenWeCall_findDeviceById() throws DeviceNotFoundException {

        when(deviceRepo.findByDeviceId("1")).thenReturn(device);
        deviceDto = deviceServiceImpl.findDeviceById("1");
        Assert.assertNotNull(deviceDto);
        Assert.assertEquals(Double.valueOf(33),deviceDto.getLat());
        Assert.assertEquals(Double.valueOf(6),deviceDto.getLon());
        verify(deviceRepo,times(1)).findByDeviceId("1");
        verifyNoMoreInteractions(deviceRepo);

    }
    @Test(expected = DeviceNotFoundException.class)
    public void shouldReturnDeviceNotFoundExceptionIfNoDeviCeIsFound() throws DeviceNotFoundException {
        when(deviceRepo.findByDeviceId("2")).thenReturn(null);
        try {
            deviceServiceImpl.findDeviceById("2");
        } catch (DeviceNotFoundException e) {
            assertEquals("device not found",e.getMessage());
            throw e; }
        fail("shouldReturnDeviceNotFoundExceptionIfNoDeviCeIsFound failed");
    }

}
