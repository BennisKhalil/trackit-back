package com.trackit.driver;

import org.springframework.beans.factory.annotation.Autowired;

public class DriverServiceImpl implements DriverService{
	
	@Autowired
	private DriverRepo driverRepo;

	@Override
	public void addDriver(Driver driver) {
		driverRepo.save(driver);
		
	}

	@Override
	public void updateDriver(Driver driver) {
		driverRepo.save(driver);

		
	}

	@Override
	public void deleteDriver(Integer id) {
		driverRepo.deleteById(id);
		
	}
	
	
	
	

}
