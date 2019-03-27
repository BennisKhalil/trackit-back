package com.trackit.driver;

import com.trackit.car.Car;
import com.trackit.car.CarRepo;
import com.trackit.enterprise.EnterpriseRepo;
import com.trackit.exception.CarsNotFoundException;
import com.trackit.exception.DriverAlreadyExistsException;
import com.trackit.exception.DriverNotFoundException;
import com.trackit.exception.EnterpriseNotFoundException;
import com.trackit.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DriverServiceImpl implements DriverService{


	@Autowired
	private DriverRepo driverRepo;

	@Autowired
	private EnterpriseRepo enterpriseRepo;

	@Autowired
	private CarRepo carRepo;

	@Override
	public List<DriverDTO> findAllDriversByEnterpriseId(Integer id) throws EnterpriseNotFoundException {
		if(!enterpriseRepo.existsById(id))
			throw new EnterpriseNotFoundException("No Enterprise Found with the id ", id);
		return mapToDriverDTOList(driverRepo.findByEnterpriseId(id));
	}

	@Override
	public DriverDTO addDriver(DriverDTO driverDTO) throws CarsNotFoundException, DriverAlreadyExistsException, EnterpriseNotFoundException {
		if(driverRepo.existsById(driverDTO.getId()))
			throw new DriverAlreadyExistsException("Driver with the Id "+driverDTO.getId()+" Already Exists");
		addOrUpdate(driverDTO);
		return driverDTO;
	}

	@Override
	public DriverDTO updateDriver(DriverDTO driverDTO) throws CarsNotFoundException, EnterpriseNotFoundException, DriverNotFoundException {
		if(!driverRepo.existsById(driverDTO.getId()))
			throw new DriverNotFoundException("No Driver Found with the Id ",driverDTO.getId());
		addOrUpdate(driverDTO);
		return driverDTO;
	}

	@Override
	public void deleteDriver(Integer id) {
		driverRepo.deleteById(id);
		
	}

	public List<DriverDTO> mapToDriverDTOList(List<Driver> drivers){


		return drivers.stream()
						.map(driver -> {
							String carId = null;
							if (driver.getCar() !=null)
								carId = driver.getCar().getId();
							return DriverDTO.builder()
									.enterprise(driver.getEnterprise().getId())
									.firstName(driver.getFirstName())
									.lastName(driver.getLastName())
									.birthDay(driver.getBirthDay().format(Utils.formatter))
									.employedDate(driver.getEmployedDate().format(Utils.formatter))
									.car(carId)
									.build();
						}).collect(Collectors.toList());

	
	}
	public Driver mapToDriver(DriverDTO driverDTO){
		Car car = null;
		if(driverDTO.getCar() != null)
			car = carRepo.getOne(driverDTO.getCar());
		return Driver.builder().Id(driverDTO.getId())
								.firstName(driverDTO.getFirstName())
								.lastName(driverDTO.getLastName())
								.birthDay(LocalDate.parse(driverDTO.getBirthDay(),Utils.formatter))
								.employedDate(LocalDate.parse(driverDTO.getBirthDay(),Utils.formatter))
								.car(car)
								.enterprise(enterpriseRepo.getOne(driverDTO.getEnterprise()))
								.build();

	}

	public void addOrUpdate(DriverDTO driverDTO) throws CarsNotFoundException, EnterpriseNotFoundException {
		if(driverDTO.getCar() != null && !carRepo.existsById(driverDTO.getCar()))
			throw new CarsNotFoundException("No Car Found with the Id ",driverDTO.getCar());
		if(!enterpriseRepo.existsById(driverDTO.getEnterprise()))
			throw new EnterpriseNotFoundException("No Enterprise Found with the Id ",driverDTO.getId());
		driverRepo.save(mapToDriver(driverDTO));
	}

}
