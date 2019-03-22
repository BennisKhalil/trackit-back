package com.trackit.car;

import com.trackit.enterprise.EnterpriseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CarDtoMapper {

    @Autowired
    private EnterpriseRepo enterpriseRepo;

    public Car map(CarDTO carDTO) {
        return Car.builder().Id(carDTO.getId())
                .brand(carDTO.getBrand())
                .fuelConsumption(carDTO.getFuelConsumption())
                .model(carDTO.getModel())
                .nextTrip(carDTO.getTrips())
                .enterprise(enterpriseRepo.getOne(carDTO.getEnterprise()))
                .build();
    }
}
