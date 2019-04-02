package com.trackit.car;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.trackit.driver.Driver;
import com.trackit.driver.DriverRepo;
import com.trackit.enterprise.EnterpriseRepo;
import com.trackit.exception.*;
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
	public CarDTO getCarByCarIdAndEntrepriseId(Integer entrepriseId, String carId) throws CarNotFoundForEntrepriseException, EnterpriseNotFoundException {
		if(!enterpriseRepo.existsById(entrepriseId))
			throw new EnterpriseNotFoundException("No Enterprise Found with the Id ", entrepriseId);
		Optional<Car> car = carRepo.findByIdAndEnterpriseId(carId,entrepriseId);
		if(!(car.isPresent())){
			throw new CarNotFoundForEntrepriseException("no car with the id "+carId+" is found in this enterprise");
		}
		return  mapToCarDTO(car.get());
	}

	@Override
	public CarDTO addCar(CarDTO carDTO,Integer enterpriseId) throws CarAlreadyExistsException, DriverNotFoundException, EnterpriseNotFoundException {
		if(carRepo.existsById(carDTO.getId()))
			throw new CarAlreadyExistsException("Car with the Id "+carDTO.getId()+" Already Exists");
		saveOrUpdate(carDTO, enterpriseId);
		return mapToCarDTO(carRepo.getOne(carDTO.getId()));
	}

	@Override
	public CarDTO updateCar(CarDTO carDTO,Integer enterpriseId) throws CarsNotFoundException, DriverNotFoundException, EnterpriseNotFoundException {
		if(!carRepo.existsById(carDTO.getId()))
			throw new CarsNotFoundException("No Car Found with the Id ", carDTO.getId());
		saveOrUpdate(carDTO,  enterpriseId);
		return mapToCarDTO(carRepo.getOne(carDTO.getId()));

	}



	@Override
	public void deleteCarById(String id,Integer enterpriseId) throws EnterpriseNotFoundException, CarNotFoundForEntrepriseException {
		if(!enterpriseRepo.existsById(enterpriseId))
			throw new EnterpriseNotFoundException("No Enterprise Found with the Id ", enterpriseId);
		if(!carRepo.findByIdAndEnterpriseId(id,enterpriseId).isPresent())
			throw new CarNotFoundForEntrepriseException("No Car Found with the Id "+ id);
		carRepo.deleteById(id);
	}

	public void saveOrUpdate(CarDTO carDTO, Integer enterpriseId) throws DriverNotFoundException, EnterpriseNotFoundException {
		if(carDTO.getDriver() !=null && !driverRepo.existsById(carDTO.getDriver()))
			throw new DriverNotFoundException("No Driver Found with the Id ",carDTO.getDriver());
		if(!enterpriseRepo.existsById(enterpriseId))
			throw new EnterpriseNotFoundException("No Enterprise Found with the Id ", enterpriseId);
		Car car = mapToCar(carDTO, enterpriseRepo, driverRepo, enterpriseId);
		carRepo.save(car);
	}




	
}
