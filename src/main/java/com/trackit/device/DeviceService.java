package com.trackit.device;


import com.trackit.exception.DeviceNotFoundException;

public interface DeviceService {

    DeviceDTO findDeviceById(String deviceId) throws DeviceNotFoundException;
}
