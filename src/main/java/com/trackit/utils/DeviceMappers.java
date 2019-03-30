package com.trackit.utils;

import com.trackit.device.Device;
import com.trackit.device.DeviceDTO;

public class DeviceMappers {
    public static DeviceDTO mapToDeviceDTO(Device device) {
        DeviceDTO deviceDTO = DeviceDTO.builder()
                .deviceId(device.getDeviceId())
                .lat(device.getLat())
                .lon(device.getLon())
                .build();
        return deviceDTO;

    }
}
