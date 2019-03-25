package com.trackit.car;

import java.util.List;
import java.util.stream.Collectors;

import com.trackit.driver.DriverDTO;
import com.trackit.driver.DriverRepo;
import com.trackit.enterprise.Enterprise;
import com.trackit.enterprise.EnterpriseRepo;
import com.trackit.exception.EnterpriseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService{

	
	@Autowired
	private CarRepo	carRepo;

	@Autowired
	private EnterpriseRepo enterpriseRepo;

	@Autowired
	private DriverRepo driverRepo;


	@Override
	public List<CarDTO> findCarsByEnterpriseId(Integer id) throws EnterpriseNotFoundException {
		if(!enterpriseRepo.findById(id).isPresent())
			throw new EnterpriseNotFoundException("No Enterprise Found with the Id ", id);
		return mapListToCarDTOs(carRepo.findByEnterpriseId(id));
	}

	@Override
	public CarDTO addOrUpdateCar(CarDTO carDTO) {
		Car car = mapToCar(carDTO);
		carRepo.save(car);
    return carDTO;
	}


	@Override
	public void deleteCarById(String id) {
		carRepo.deleteById(id);
	}



	public Car mapToCar(CarDTO carDTO) {
		return Car.builder()
				.Id(carDTO.getId())
				.brand(carDTO.getBrand())
				.fuelConsumption(carDTO.getFuelConsumption())
				.model(carDTO.getModel())
				.nextTrip(carDTO.getTrip())
				.driver(driverRepo.getOne(carDTO.getDriver()))
				.enterprise(enterpriseRepo.getOne(carDTO.getEnterprise()))
				.build();
	}



	public List<CarDTO> mapListToCarDTOs(List<Car> cars){
		return cars.parallelStream().map(c -> {
			Integer driverId = null;
			if(c.getDriver() != null)
				driverId= c.getDriver().getId();
			return CarDTO.builder()
					.Id(c.getId())
					.brand(c.getBrand())
					.model(c.getModel())
					.trip(c.getNextTrip())
					.fuelConsumption(c.getFuelConsumption())
					.driver(c.getDriver().getId())
					.enterprise(c.getEnterprise().getId())
					.build();
		}).collect(Collectors.toList());
	}




	
}
