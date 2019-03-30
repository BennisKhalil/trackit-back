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

import static com.trackit.utils.CarMappers.*;

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
	public CarDTO getCar(String id) throws CarsNotFoundException {
		if(!carRepo.existsById(id))
			throw new CarsNotFoundException("No Car Found with the Id ",id);
		return mapToCarDTO(carRepo.getOne(id));
	}

	@Override
	public CarDTO addCar(CarDTO carDTO) throws CarAlreadyExistsException, DriverNotFoundException, EnterpriseNotFoundException {
		if(carRepo.existsById(carDTO.getId()))
			throw new CarAlreadyExistsException("Car with the Id "+carDTO.getId()+" Already Exists");
		saveOrUpdate(carDTO);
		return mapToCarDTO(carRepo.getOne(carDTO.getId()));
	}

	@Override
	public CarDTO updateCar(CarDTO carDTO) throws CarsNotFoundException, DriverNotFoundException, EnterpriseNotFoundException {
		if(!carRepo.existsById(carDTO.getId()))
			throw new CarsNotFoundException("No Car Found with the Id ", carDTO.getId());
		saveOrUpdate(carDTO);
		return mapToCarDTO(carRepo.getOne(carDTO.getId()));

	}



	@Override
	public void deleteCarById(String id) throws CarsNotFoundException {
		if(!carRepo.existsById(id))
			throw new CarsNotFoundException("No Car Found with the Id ",id);
		carRepo.deleteById(id);
	}

	public void saveOrUpdate(CarDTO carDTO) throws DriverNotFoundException, EnterpriseNotFoundException {
		if(carDTO.getDriver() !=null && !driverRepo.existsById(carDTO.getDriver()))
			throw new DriverNotFoundException("No Driver Found with the Id ",carDTO.getDriver());
		if(!enterpriseRepo.existsById(carDTO.getEnterprise()))
			throw new EnterpriseNotFoundException("No Enterprise Found with the Id ", carDTO.getEnterprise());
		Car car = mapToCar(carDTO, enterpriseRepo, driverRepo);
		carRepo.save(car);
	}




	
}
