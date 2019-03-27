package com.trackit.device;

import com.trackit.exception.DeviceNotFoundException;

public interface DeviceService {
	DeviceDto findDeviceById(String deviceId) throws DeviceNotFoundException

			;

}
