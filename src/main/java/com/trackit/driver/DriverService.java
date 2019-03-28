package com.trackit.driver;

import com.trackit.exception.CarsNotFoundException;
import com.trackit.exception.DriverAlreadyExistsException;
import com.trackit.exception.DriverNotFoundException;
import com.trackit.exception.EnterpriseNotFoundException;

import java.util.List;

public interface DriverService {

	List<DriverDTO> findAllDriversByEnterpriseId(Integer id) throws EnterpriseNotFoundException;
	DriverDTO getDriver(Integer id) throws DriverNotFoundException;
	DriverDTO addDriver(DriverDTO driverDTO) throws CarsNotFoundException, DriverAlreadyExistsException, EnterpriseNotFoundException;
	DriverDTO updateDriver(DriverDTO driverDTO) throws CarsNotFoundException, EnterpriseNotFoundException, DriverNotFoundException;
	void deleteDriver(Integer id);
}
