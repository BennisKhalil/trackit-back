package com.trackit.car;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepo extends JpaRepository<Car, String>{
	@Query("select * from car c where c.enterprise_id = :id")
	public List<Car> findByEnterpriseId(Integer id);
}
