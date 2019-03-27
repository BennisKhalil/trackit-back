package com.trackit.driver;

import com.trackit.enterprise.Enterprise;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverRepo extends JpaRepository<Driver, Integer>{

    @Query("select * from driver d where d.enterprise_id = :id")
    List<Driver> findByEnterpriseId(Integer id);
}
