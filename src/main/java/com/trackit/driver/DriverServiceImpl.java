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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Service
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
	public DriverDTO getDriver(Integer id) throws DriverNotFoundException {
		if(!driverRepo.existsById(id)){
			throw new DriverNotFoundException("No Driver Found with the Id ",id);
		}
		return maptoDriverDTO(driverRepo.getOne(id));
	}

	@Override
	public DriverDTO addDriver(DriverDTO driverDTO) throws CarsNotFoundException, EnterpriseNotFoundException {
		Driver driver = addOrUpdate(driverDTO);
		return maptoDriverDTO(driver);
	}

	@Override
	public DriverDTO updateDriver(DriverDTO driverDTO) throws CarsNotFoundException, EnterpriseNotFoundException, DriverNotFoundException {
		if(!driverRepo.existsById(driverDTO.getId()))
			throw new DriverNotFoundException("No Driver Found with the Id ",driverDTO.getId());
		Driver driver = addOrUpdate(driverDTO);
		return maptoDriverDTO(driver);
	}

	@Override
	public void deleteDriver(Integer id) throws DriverNotFoundException {
		if(!driverRepo.existsById(id))
			throw new DriverNotFoundException("No Driver Found with the Id ",id);
		driverRepo.deleteById(id);
		
	}



	public Driver addOrUpdate(DriverDTO driverDTO) throws CarsNotFoundException, EnterpriseNotFoundException {
		if(driverDTO.getCar() != null && !carRepo.existsById(driverDTO.getCar()))
			throw new CarsNotFoundException("No Car Found with the Id ",driverDTO.getCar());
		if(!enterpriseRepo.existsById(driverDTO.getEnterprise()))
			throw new EnterpriseNotFoundException("No Enterprise Found with the Id ",driverDTO.getEnterprise());
		Driver driver = mapToDriver(driverDTO);
		return driverRepo.save(driver);
	}


	//Should Move Mappers To Utils

	public List<DriverDTO> mapToDriverDTOList(List<Driver> drivers){


		return drivers.stream()
						.map(driver -> {
							String carId = null;
							if (driver.getCar() !=null)
								carId = driver.getCar().getId();
							return DriverDTO.builder()
									.id(driver.getId())
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
		return Driver.builder().id(driverDTO.getId())
								.firstName(driverDTO.getFirstName())
								.lastName(driverDTO.getLastName())
								.birthDay(LocalDate.parse(driverDTO.getBirthDay(),Utils.formatter))
								.employedDate(LocalDate.parse(driverDTO.getEmployedDate(),Utils.formatter))
								.car(car)
								.enterprise(enterpriseRepo.getOne(driverDTO.getEnterprise()))
								.build();

	}



	public DriverDTO maptoDriverDTO(Driver driver){
		String carId = null;
		if(driver.getCar() != null)
			carId = driver.getCar().getId();
		return DriverDTO.builder().id(driver.getId())
				.firstName(driver.getFirstName())
				.lastName(driver.getLastName())
				.birthDay(driver.getBirthDay().format(Utils.formatter))
				.employedDate(driver.getEmployedDate().format(Utils.formatter))
				.car(carId)
				.enterprise(driver.getEnterprise().getId())
				.build();
	}

}
