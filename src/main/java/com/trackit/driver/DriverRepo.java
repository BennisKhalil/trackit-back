package com.trackit.driver;

import com.trackit.enterprise.Enterprise;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DriverRepo extends JpaRepository<Driver, Integer>{

    List<Driver> findByEnterpriseId(Integer id);

    public Optional<Driver> findByEnterpriseIdAndId(Integer idEnterprise,Integer idDriver);

}
