package com.trackit.Device;


import com.trackit.exception.DeviceNotFoundException;
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

    public static final String id = "1";

    @Mock
    private DeviceRepo deviceRepo;

    @InjectMocks
    private DeviceServiceImpl deviceServiceImpl;

    private Device device;
    private DeviceDto deviceDto;

    @Before
    public void init() {

        device = Device.builder()
                .deviceId(id).lat("33.97").lon("-6.64").build();

    }

    @Test
    public void shouldReturnDeviceWhenWeCall_findDeviceById() throws DeviceNotFoundException {
        when(deviceRepo.findByDeviceId(id)).thenReturn(device);
        deviceDto = deviceServiceImpl.findDeviceById(id);

        Assert.assertNotNull(deviceDto);
        Assert.assertEquals("33.97",deviceDto.getLat());
        Assert.assertEquals("-6.64",deviceDto.getLon());
        verify(deviceRepo,times(1)).findByDeviceId(id);
        verifyNoMoreInteractions(deviceRepo);

    }
    @Test(expected = DeviceNotFoundException.class)
    public void shouldReturnDeviceNotFoundExceptionIfNoDeviCeIsFound() throws DeviceNotFoundException {
        when(deviceRepo.findByDeviceId(id)).thenReturn(null);
        try {
            deviceServiceImpl.findDeviceById(id);
        } catch (DeviceNotFoundException e) {
            assertEquals("device not found",e.getMessage());
            throw e; }
        fail("shouldReturnDeviceNotFoundExceptionIfNoDeviCeIsFound failed");
    }

}
