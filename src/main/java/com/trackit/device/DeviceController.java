package com.trackit.device;
import com.trackit.exception.DeviceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	@GetMapping("/{deviceId}")
	public ResponseEntity<DeviceDto> getDeviceById(@PathVariable String deviceId) throws DeviceNotFoundException {
		return new ResponseEntity<DeviceDto>(deviceService.findDeviceById(deviceId),HttpStatus.OK);
		
	}
}
