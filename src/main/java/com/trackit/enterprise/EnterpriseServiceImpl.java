package com.trackit.enterprise;

import java.util.List;
import java.util.stream.Collectors;

import com.trackit.car.Car;
import com.trackit.car.CarRepo;
import com.trackit.driver.Driver;
import com.trackit.driver.DriverRepo;
import com.trackit.exception.EnterpriseAlreadyExistsException;
import com.trackit.exception.EnterpriseNotFoundException;
import com.trackit.utils.EnterpriseMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.trackit.utils.EnterpriseMappers.mapToEnterpriseDTOList;

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
		return EnterpriseMappers.mapToEnterpriseDTO(enterpriseRepo.getOne(id));
	}



	@Override
	public EnterpriseDTO addEnterprise(EnterpriseDTO enterpriseDTO) throws EnterpriseAlreadyExistsException {
		Enterprise enterprise = enterpriseRepo.saveAndFlush(EnterpriseMappers.maptoEnterprise(enterpriseDTO,carRepo, driverRepo));
		return EnterpriseMappers.mapToEnterpriseDTO(enterprise);
	}

	@Override
	public EnterpriseDTO updateEnterprise(EnterpriseDTO enterpriseDTO) throws EnterpriseNotFoundException {
		if (!enterpriseRepo.existsById(enterpriseDTO.getId()))
			throw new EnterpriseNotFoundException("No Enterprise Found with the Id ",enterpriseDTO.getId());
		Enterprise enterprise = enterpriseRepo.saveAndFlush(EnterpriseMappers.maptoEnterprise(enterpriseDTO,carRepo, driverRepo));
		return  EnterpriseMappers.mapToEnterpriseDTO(enterprise);
	}


	@Override
	public void deleteEnterprise(Integer id) {
		enterpriseRepo.deleteById(id);
		
	}


}
