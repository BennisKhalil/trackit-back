package com.trackit.driver;

import com.trackit.enterprise.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverRepo extends JpaRepository<Driver, Integer>{

    List<Driver> findByEnteprise(Enterprise enterprise);
}
