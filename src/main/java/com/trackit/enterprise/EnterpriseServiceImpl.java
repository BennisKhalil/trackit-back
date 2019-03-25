package com.trackit.enterprise;

import java.util.List;
import java.util.stream.Collectors;

import com.trackit.car.Car;
import com.trackit.car.CarRepo;
import com.trackit.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseServiceImpl implements EnterpriseService{

	@Autowired
	private EnterpriseRepo enterpriseRepo;

	@Autowired
	private CarRepo carRepo;
	@Override
	public List<EnterpriseDTO> findAllEnterprise() {
		return mapToEnterpriseDTOList(enterpriseRepo.findAll());
	}

	@Override
	public EnterpriseDTO addOrUpdateEntreprise(EnterpriseDTO enterpriseDTO) {
		enterpriseRepo.save(maptoEnterprise(enterpriseDTO));
		return enterpriseDTO;
	}


	@Override
	public void deleteEnterprise(Integer id) {
		enterpriseRepo.deleteById(id);
		
	}

	public Enterprise maptoEnterprise(EnterpriseDTO enterpriseDTO){
		List<String> carsIds = enterpriseDTO.getCarsIds();
		List<Car> cars = null;
		if (carsIds != null) {
			cars = carsIds.parallelStream()
					       .map(id -> carRepo.findById(id).get())
					       .collect(Collectors.toList());
		}

		return Enterprise.builder()
				.id(enterpriseDTO.getId())
				.name(enterpriseDTO.getName())
				.address(enterpriseDTO.getAddress())
				.cars(cars)
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

}
