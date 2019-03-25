package com.trackit.driver;

import com.trackit.car.Car;
import com.trackit.car.CarRepo;
import com.trackit.enterprise.Enterprise;
import com.trackit.enterprise.EnterpriseRepo;
import com.trackit.exception.CarsNotFoundException;
import com.trackit.exception.EnterpriseNotFoundException;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DriverServiceImpl implements DriverService{

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
	@Autowired
	private DriverRepo driverRepo;

	@Autowired
	private EnterpriseRepo enterpriseRepo;

	@Autowired
	private CarRepo carRepo;

	@Override
	public List<DriverDTO> findAllDriversByEnterpriseId(Integer id) throws EnterpriseNotFoundException {
		Optional<Enterprise> enterprise = enterpriseRepo.findById(id);
		if(!enterprise.isPresent())
			throw new EnterpriseNotFoundException("No Enterprise Found with the id ", id);

		return mapToDriverDTOList(driverRepo.findByEnteprise(enterprise.get()));
	}

	@Override
	public DriverDTO addOrUpdateDriver(DriverDTO driverDTO) throws CarsNotFoundException, EnterpriseNotFoundException {
		if(!carRepo.findById(driverDTO.getCar()).isPresent())
			throw new CarsNotFoundException("No Car Found with the Id ",driverDTO.getCar());
		if(!enterpriseRepo.findById(driverDTO.getEnterprise()).isPresent())
			throw new EnterpriseNotFoundException("No Enterprise Found with the Id ",driverDTO.getEnterprise());

		driverRepo.save(mapToDriver(driverDTO));
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
									.birthDay(driver.getBirthDay().format(formatter))
									.employedDate(driver.getEmployedDate().format(formatter))
									.car(carId)
									.build();
						}).collect(Collectors.toList());

	
	}
	public Driver mapToDriver(DriverDTO driverDTO){
		return Driver.builder().Id(driverDTO.getId())
								.firstName(driverDTO.getFirstName())
								.lastName(driverDTO.getLastName())
								.birthDay(LocalDate.parse(driverDTO.getBirthDay(),formatter))
								.employedDate(LocalDate.parse(driverDTO.getBirthDay(),formatter))
								.car(carRepo.getOne(driverDTO.getCar()))
								.enterprise(enterpriseRepo.getOne(driverDTO.getEnterprise()))
								.build();

	}

}
