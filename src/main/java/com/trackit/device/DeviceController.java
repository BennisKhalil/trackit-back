package com.trackit.device;

import com.trackit.exception.DeviceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @CrossOrigin("http://localhost:3000")
    @GetMapping(path="/{deviceId}", produces=MediaType.APPLICATION_STREAM_JSON_VALUE)
    public ResponseEntity<DeviceDTO> getDeviceById(@PathVariable String deviceId) throws DeviceNotFoundException {
        DeviceDTO deviceDTO = deviceService.findDeviceById(deviceId);
        return new ResponseEntity<DeviceDTO>(deviceDTO,HttpStatus.OK);

    }


}
