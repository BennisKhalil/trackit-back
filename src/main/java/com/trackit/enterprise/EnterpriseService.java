package com.trackit.enterprise;

import com.trackit.exception.EnterpriseAlreadyExistsException;
import com.trackit.exception.EnterpriseNotFoundException;

import java.util.List;

public interface EnterpriseService {

	List<EnterpriseDTO> findAllEnterprise();
	EnterpriseDTO findEnterpriseById(Integer id) throws EnterpriseNotFoundException;
	EnterpriseDTO addEnterprise(EnterpriseDTO enterpriseDTO) throws EnterpriseAlreadyExistsException;
	EnterpriseDTO updateEnterprise(EnterpriseDTO enterpriseDTO) throws EnterpriseAlreadyExistsException, EnterpriseNotFoundException;
	void deleteEnterprise(Integer id) throws EnterpriseNotFoundException;
}
