package com.trackit.car;

import java.util.List;
import java.util.stream.Collectors;

import com.trackit.driver.Driver;
import com.trackit.driver.DriverRepo;
import com.trackit.enterprise.EnterpriseRepo;
import com.trackit.exception.CarAlreadyExistsException;
import com.trackit.exception.CarsNotFoundException;
import com.trackit.exception.EnterpriseNotFoundException;
import com.trackit.exception.DriverNotFoundException;
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
		if(!enterpriseRepo.existsById(id))
			throw new EnterpriseNotFoundException("No Enterprise Found with the Id ", id);
		return mapListToCarDTOs(carRepo.findByEnterpriseId(id));
	}

	@Override
	public CarDTO addCar(CarDTO carDTO) throws CarAlreadyExistsException, DriverNotFoundException, EnterpriseNotFoundException {
		if(carRepo.existsById(carDTO.getId()))
			throw new CarAlreadyExistsException("Car with the Id "+carDTO.getId()+" Already Exists");
		saveOrUpdate(carDTO);
		return carDTO;
	}

	@Override
	public CarDTO updateCar(CarDTO carDTO) throws CarsNotFoundException, DriverNotFoundException, EnterpriseNotFoundException {
		if(!carRepo.existsById(carDTO.getId()))
			throw new CarsNotFoundException("No Car Found with the Id ", carDTO.getId());
		saveOrUpdate(carDTO);
		return carDTO;
	}



	@Override
	public void deleteCarById(String id) {
		carRepo.deleteById(id);
	}



	public Car mapToCar(CarDTO carDTO) {
		Driver driver = null;
		if(carDTO.getDriver() != null)
			driver =driverRepo.getOne(carDTO.getDriver());
		return Car.builder()
				.Id(carDTO.getId())
				.brand(carDTO.getBrand())
				.fuelConsumption(carDTO.getFuelConsumption())
				.model(carDTO.getModel())
				.nextTrip(carDTO.getTrip())
				.driver(driver)
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
					.driver(driverId)
					.enterprise(c.getEnterprise().getId())
					.build();
		}).collect(Collectors.toList());
	}


	public void saveOrUpdate(CarDTO carDTO) throws DriverNotFoundException, EnterpriseNotFoundException {
		if(carDTO.getDriver() !=null && !driverRepo.existsById(carDTO.getDriver()))
			throw new DriverNotFoundException("No Driver Found with the Id ",carDTO.getDriver());
		if(!enterpriseRepo.existsById(carDTO.getEnterprise()))
			throw new EnterpriseNotFoundException("No Enterprise Found with the Id ", carDTO.getEnterprise());
		Car car = mapToCar(carDTO);
		carRepo.save(car);
	}


	
}
