package com.trackit.device;
import com.trackit.exception.DeviceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DeviceServiceImpl implements DeviceService{
	
	@Autowired
	private DeviceRepo deviceRepo;

	public DeviceDto mapToDto(Device device) {
		DeviceDto deviceDto = DeviceDto.builder().deviceId(device.getDeviceId())
				.lat(device.getLat()).lon(device.getLon()).build();
				
		return deviceDto;
	}
	
	@Override
	public DeviceDto findDeviceById(String deviceId) throws DeviceNotFoundException
	{
		Device device =deviceRepo.findByDeviceId(deviceId);
		if(device==null) {
			throw new DeviceNotFoundException("device not found");
		}
		return mapToDto(device) ;
	}

}
