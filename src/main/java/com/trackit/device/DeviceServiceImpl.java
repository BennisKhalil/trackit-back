package com.trackit.device;

import com.trackit.exception.DeviceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.trackit.utils.DeviceMappers.mapToDeviceDTO;


@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepo deviceRepo;

    public DeviceDTO findDeviceById(String deviceId) throws DeviceNotFoundException {
        Device device = deviceRepo.findByDeviceId(deviceId);
        if(device==null) {
            throw new DeviceNotFoundException("device not found");
        }
        return mapToDeviceDTO(device);

    }

}
