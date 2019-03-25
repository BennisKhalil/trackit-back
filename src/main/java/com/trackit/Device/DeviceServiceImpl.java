package com.trackit.Device;

import com.trackit.exception.DeviceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepo deviceRepo;

    public DeviceDto mapToDeviceDto(Device device)  {

        return DeviceDto.builder()
                .deviceId(device.getDeviceId())
                .lat(device.getLat())
                .lon(device.getLon())
                .build();

    }

    @Override
    public DeviceDto findDeviceById(String deviceId) throws DeviceNotFoundException {
        Device device = deviceRepo.findByDeviceId(deviceId);
        if(device==null){
            throw new DeviceNotFoundException("device not found");
        }
        DeviceDto deviceDto = mapToDeviceDto(device);

        return  deviceDto;
    }


}
