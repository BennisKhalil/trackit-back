package com.trackit.driver;

import com.trackit.exception.*;

import java.util.List;

public interface DriverService {

	List<DriverDTO> findAllDriversByEnterpriseId(Integer id) throws EnterpriseNotFoundException;
	DriverDTO getDriverByDriverIdAndEntrepriseId(Integer EntrepriseId, Integer DriverId) throws DriverNotFoundForEnterpriseException, EnterpriseNotFoundException;
	DriverDTO addDriver(DriverDTO driverDTO, Integer enterpriseId) throws CarsNotFoundException, DriverAlreadyExistsException, EnterpriseNotFoundException;
	DriverDTO updateDriver(DriverDTO driverDTO, Integer enterpriseId) throws CarsNotFoundException, EnterpriseNotFoundException, DriverNotFoundException;
	void deleteDriver(Integer id, Integer enterpriseId) throws DriverNotFoundException, DriverNotFoundForEnterpriseException, EnterpriseNotFoundException;
}
