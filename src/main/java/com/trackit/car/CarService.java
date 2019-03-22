package com.trackit.car;

import java.util.List;

public interface CarService {

	List<Car> findCarsByEnterpriseId(Integer id);
	void addCar(Car car);
	void updateCar(Car car);
	void DeleteCar(String id);
	
}
