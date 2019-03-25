package com.trackit.enterprise;

import java.util.List;

public interface EnterpriseService {

	List<EnterpriseDTO> findAllEnterprise();
	EnterpriseDTO addOrUpdateEntreprise(EnterpriseDTO enterpriseDTO);
	void deleteEnterprise(Integer id);
}
