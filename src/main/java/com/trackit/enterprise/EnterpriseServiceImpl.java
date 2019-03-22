package com.trackit.enterprise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class EnterpriseServiceImpl implements EnterpriseService{

	@Autowired
	private EnterpriseRepo enterpriseRepo;
	@Override
	public List<Enterprise> findAllEnterprise() {
		return enterpriseRepo.findAll();
	}

	@Override
	public void addEntreprise(Enterprise enterprise) {
		enterpriseRepo.save(enterprise);
		
	}

	@Override
	public void updateEnterprise(Enterprise enterprise) {
		enterpriseRepo.save(enterprise);
		
	}

	@Override
	public void deleteEnterprise(Integer id) {
		enterpriseRepo.deleteById(id);
		
	}

}
