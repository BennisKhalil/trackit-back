package com.trackit.device;

import com.trackit.exception.DeviceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepo deviceRepo;

    public DeviceDTO mapToDeviceDTO(Device device) {
        DeviceDTO deviceDTO = DeviceDTO.builder()
                .deviceId(device.getDeviceId())
                .lat(device.getLat())
                .lon(device.getLon())
                .build();
        return deviceDTO;

    }

    public DeviceDTO findDeviceById(String deviceId) throws DeviceNotFoundException {
        Device device = deviceRepo.findByDeviceId(deviceId);
        if(device==null) {
            throw new DeviceNotFoundException("device not found");
        }
        return mapToDeviceDTO(device);

    }

}
