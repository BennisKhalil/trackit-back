package com.trackit.car;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarRepo extends JpaRepository<Car, String>{
	 List<Car> findByEnterpriseId(Integer idEnterprise);

	 Optional<Car> findByIdAndEnterpriseId(String idCar, Integer idEnterprise);
}
