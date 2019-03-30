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

import static com.trackit.utils.DriverMappers.*;

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
		Driver driver = mapToDriver(driverDTO,enterpriseRepo, carRepo);
		return driverRepo.save(driver);
	}



}
