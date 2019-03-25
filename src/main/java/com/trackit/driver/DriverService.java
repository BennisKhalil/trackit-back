package com.trackit.driver;

import com.trackit.exception.CarsNotFoundException;
import com.trackit.exception.EnterpriseNotFoundException;

import java.util.List;

public interface DriverService {

	List<DriverDTO> findAllDriversByEnterpriseId(Integer id) throws EnterpriseNotFoundException;
	DriverDTO addOrUpdateDriver(DriverDTO driverDTO) throws CarsNotFoundException, EnterpriseNotFoundException;
	void deleteDriver(Integer id);
}
