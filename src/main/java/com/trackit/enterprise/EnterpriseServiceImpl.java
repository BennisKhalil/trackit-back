package com.trackit.enterprise;

import java.util.List;
import java.util.stream.Collectors;

import com.trackit.car.Car;
import com.trackit.car.CarRepo;
import com.trackit.driver.Driver;
import com.trackit.driver.DriverRepo;
import com.trackit.exception.EnterpriseAlreadyExistsException;
import com.trackit.exception.EnterpriseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseServiceImpl implements EnterpriseService{

	@Autowired
	private EnterpriseRepo enterpriseRepo;

	@Autowired
	private CarRepo carRepo;

	@Autowired
	private DriverRepo driverRepo;

	@Override
	public List<EnterpriseDTO> findAllEnterprise() {
		return mapToEnterpriseDTOList(enterpriseRepo.findAll());
	}

	@Override
	public EnterpriseDTO findEnterpriseById(Integer id) throws EnterpriseNotFoundException {
		if(!enterpriseRepo.existsById(id))
			throw new EnterpriseNotFoundException("No Enterprise Found with the Id ",id);
		return mapToEnterpriseDTO(enterpriseRepo.getOne(id));
	}



	@Override
	public EnterpriseDTO addEnterprise(EnterpriseDTO enterpriseDTO) throws EnterpriseAlreadyExistsException {
		Enterprise enterprise = enterpriseRepo.saveAndFlush(maptoEnterprise(enterpriseDTO));
		return mapToEnterpriseDTO(enterprise);
	}

	@Override
	public EnterpriseDTO updateEnterprise(EnterpriseDTO enterpriseDTO) throws EnterpriseNotFoundException {
		if (!enterpriseRepo.existsById(enterpriseDTO.getId()))
			throw new EnterpriseNotFoundException("No Enterprise Found with the Id ",enterpriseDTO.getId());
		Enterprise enterprise = enterpriseRepo.saveAndFlush(maptoEnterprise(enterpriseDTO));
		return  mapToEnterpriseDTO(enterprise);
	}


	@Override
	public void deleteEnterprise(Integer id) {
		enterpriseRepo.deleteById(id);
		
	}

	public Enterprise maptoEnterprise(EnterpriseDTO enterpriseDTO){
		List<String> carsIds = enterpriseDTO.getCarsIds();
		List<Integer> driverIds = enterpriseDTO.getDriverIds();
		List<Car> cars = null;
		List<Driver> drivers = null;
		if (carsIds != null) {
			cars = carsIds.parallelStream()
					       .map(id -> carRepo.findById(id).get())
					       .collect(Collectors.toList());
		}
		if (driverIds != null) {
			drivers = driverIds.parallelStream()
					.map(id -> driverRepo.findById(id).get())
					.collect(Collectors.toList());
		}

		return Enterprise.builder()
				.id(enterpriseDTO.getId())
				.name(enterpriseDTO.getName())
				.address(enterpriseDTO.getAddress())
				.cars(cars)
				.drivers(drivers)
				.nbCars(enterpriseDTO.getNbCars())
				.build();
	}

	public List<EnterpriseDTO> mapToEnterpriseDTOList(List<Enterprise> enterprises){

		return enterprises.stream()
				.map(enterprise -> {
					List<Car> cars = enterprise.getCars();
					List<Driver> drivers = enterprise.getDrivers();

					List<String> cardIds = null;
					List<Integer> driverIds = null;

					if(cars != null){
						cardIds = cars
								.stream()
								.map(Car::getId)
								.collect(Collectors.toList());
					}
					if(drivers !=null){
						driverIds = drivers
									.stream()
									.map(Driver::getId)
									.collect(Collectors.toList());
					}

					return EnterpriseDTO.builder()
							.id(enterprise.getId())
							.name(enterprise.getName())
							.address(enterprise.getAddress())
							.carsIds(cardIds)
							.driverIds(driverIds)
							.nbCars(enterprise.getNbCars())
							.build();
				}).collect(Collectors.toList());
	}

	public static EnterpriseDTO mapToEnterpriseDTO(Enterprise enterprise){
		List<Car> cars = enterprise.getCars();
		List<Driver> drivers = enterprise.getDrivers();
		List<String> carsIds = null;
		List<Integer> driverIds = null;

		if (cars != null) {
			carsIds = cars.parallelStream()
					.map(car -> car.getId())
					.collect(Collectors.toList());
		}
		if (drivers != null) {
			driverIds = drivers.stream()
					.map(driver -> driver.getId())
					.collect(Collectors.toList());
			System.out.println(drivers);
		}

		return EnterpriseDTO.builder()
				.id(enterprise.getId())
				.name(enterprise.getName())
				.address(enterprise.getAddress())
				.carsIds(carsIds)
				.driverIds(driverIds)
				.nbCars(enterprise.getNbCars())
				.build();
	}

}
