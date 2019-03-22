package com.trackit.car;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService{

	
	@Autowired
	private CarRepo	carRepo;
	
	@Override
	public List<Car> findCarsByEnterpriseId(Integer id) {
		return carRepo.findByEnterpriseId(id);
	}

	@Override
	public void addCar(Car car) {
		carRepo.save(car);
	}

	@Override
	public void updateCar(Car car) {
		carRepo.save(car);
		
	}

	@Override
	public void DeleteCar(String id) {
		carRepo.deleteById(id);		
	}

	
	
}
