package com.trackit.car;

import java.util.List;

import com.trackit.enterprise.Enterprise;
import com.trackit.enterprise.EnterpriseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService{

	
	@Autowired
	private CarRepo	carRepo;

	@Autowired
	private EnterpriseRepo enterpriseRepo;


	@Override
	public List<Car> findCarsByEnterpriseId(Integer id) {

		return carRepo.findByEnterpriseId(id);
	}

	@Override
	public CarDTO addOrUpdateCar(CarDTO carDTO) {
		Car car = map(carDTO);
		carRepo.save(car);
    return carDTO;
	}


	@Override
	public void deleteCarById(String id) {
		carRepo.deleteById(id);
	}

	public Car map(CarDTO carDTO) {
		return Car.builder().Id(carDTO.getId())
				.brand(carDTO.getBrand())
				.fuelConsumption(carDTO.getFuelConsumption())
				.model(carDTO.getModel())
				.nextTrip(carDTO.getTrips())
				.enterprise(enterpriseRepo.getOne(carDTO.getEnterprise()))
				.build();
	}


	
}
