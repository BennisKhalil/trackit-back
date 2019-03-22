package com.trackit.enterprise;

import java.util.List;

public interface EnterpriseService {

	List<Enterprise> findAllEnterprise(); 
	void addEntreprise(Enterprise enterprise);
	void updateEnterprise(Enterprise enterprise);
	void deleteEnterprise(Integer id);
}
