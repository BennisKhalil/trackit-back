package com.trackit.Device;

import com.trackit.exception.DeviceNotFoundException;

import java.util.List;

public interface DeviceService {
    public DeviceDto findDeviceById(String deviceId) throws DeviceNotFoundException;
}
