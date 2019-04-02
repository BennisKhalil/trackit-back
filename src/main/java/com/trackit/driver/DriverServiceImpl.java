package com.trackit.driver;

import com.trackit.car.CarRepo;
import com.trackit.enterprise.EnterpriseRepo;
import com.trackit.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
			throw new EnterpriseNotFoundException("No Enterprise Found with the Id ", id);
		return mapToDriverDTOList(driverRepo.findByEnterpriseId(id));
	}


	@Override
	public DriverDTO getDriverByDriverIdAndEntrepriseId(Integer entrepriseId, Integer driverId) throws DriverNotFoundForEnterpriseException, EnterpriseNotFoundException {
		if(!enterpriseRepo.existsById(entrepriseId))
			throw new EnterpriseNotFoundException("No Enterprise Found with the Id ", entrepriseId);
		Optional<Driver> driver = driverRepo.findByEnterpriseIdAndId(entrepriseId,driverId);
		if(!driver.isPresent())
			throw new DriverNotFoundForEnterpriseException("no driver with the id "+driverId+" is found in this enterprise");
		return mapToDriverDTO(driver.get());
	}


	@Override
	public DriverDTO addDriver(DriverDTO driverDTO, Integer enterpriseId) throws CarsNotFoundException, EnterpriseNotFoundException, DriverAlreadyExistsException {
		Driver driver = addOrUpdate(driverDTO,enterpriseId);
		return mapToDriverDTO(driver);
	}

	@Override
	public DriverDTO updateDriver(DriverDTO driverDTO, Integer enterpriseId) throws CarsNotFoundException, EnterpriseNotFoundException, DriverNotFoundException {
		if(!driverRepo.existsById(driverDTO.getId()))
			throw new DriverNotFoundException("No Driver Found with the Id ",driverDTO.getId());
		Driver driver = addOrUpdate(driverDTO,enterpriseId);
		return mapToDriverDTO(driver);
	}

	@Override
	public void deleteDriver(Integer id, Integer enterpriseId) throws DriverNotFoundForEnterpriseException, EnterpriseNotFoundException {
		if(!enterpriseRepo.existsById(enterpriseId))
			throw new EnterpriseNotFoundException("No Enterprise Found with the Id ", enterpriseId);
		if(!driverRepo.findByEnterpriseIdAndId(enterpriseId, id).isPresent())
			throw new DriverNotFoundForEnterpriseException("No Driver Found with the Id "+ id);
		driverRepo.deleteById(id);
		
	}





	public Driver addOrUpdate(DriverDTO driverDTO, Integer enterpriseId) throws CarsNotFoundException, EnterpriseNotFoundException {
		if(driverDTO.getCar() != null && !carRepo.existsById(driverDTO.getCar()))
			throw new CarsNotFoundException("No Car Found with the Id ",driverDTO.getCar());
		if(!enterpriseRepo.existsById(enterpriseId))
			throw new EnterpriseNotFoundException("No Enterprise Found with the Id ",enterpriseId);
		Driver driver = mapToDriver(driverDTO,enterpriseRepo, carRepo,enterpriseId);
		return driverRepo.save(driver);
	}



}
