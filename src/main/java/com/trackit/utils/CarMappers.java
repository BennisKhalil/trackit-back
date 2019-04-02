package com.trackit.utils;

import com.trackit.car.Car;
import com.trackit.car.CarDTO;
import com.trackit.driver.Driver;
import com.trackit.driver.DriverRepo;
import com.trackit.enterprise.EnterpriseRepo;

import java.util.List;
import java.util.stream.Collectors;

public class CarMappers {


    public static Car mapToCar(CarDTO carDTO, EnterpriseRepo enterpriseRepo, DriverRepo driverRepo,Integer enterpriseId) {
        Driver driver = null;
        if(carDTO.getDriver() != null)
            driver =driverRepo.getOne(carDTO.getDriver());
        return Car.builder()
                .id(carDTO.getId())
                .brand(carDTO.getBrand())
                .fuelConsumption(carDTO.getFuelConsumption())
                .model(carDTO.getModel())
                .nextTrip(carDTO.getTrip())
                .driver(driver)
                .enterprise(enterpriseRepo.getOne(enterpriseId))
                .build();
    }



    public static List<CarDTO> mapListToCarDTOs(List<Car> cars){
        return cars.parallelStream().map(c -> {
            Integer driverId = null;
            if(c.getDriver() != null)
                driverId= c.getDriver().getId();
            return CarDTO.builder()
                    .id(c.getId())
                    .brand(c.getBrand())
                    .model(c.getModel())
                    .trip(c.getNextTrip())
                    .fuelConsumption(c.getFuelConsumption())
                    .driver(driverId)
                    .build();
        }).collect(Collectors.toList());
    }




    public static CarDTO mapToCarDTO(Car car){
        Integer driverId = null;
        if(car.getDriver() != null)
            driverId = car.getDriver().getId();
        return CarDTO.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .fuelConsumption(car.getFuelConsumption())
                .model(car.getModel())
                .trip(car.getNextTrip())
                .driver(driverId)
                .build();
    }
}
