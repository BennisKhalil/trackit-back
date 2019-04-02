package com.trackit.utils;

import com.trackit.car.Car;
import com.trackit.car.CarRepo;
import com.trackit.driver.Driver;
import com.trackit.driver.DriverDTO;
import com.trackit.enterprise.Enterprise;
import com.trackit.enterprise.EnterpriseRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DriverMappers {

    public static List<DriverDTO> mapToDriverDTOList(List<Driver> drivers){


        return drivers.stream()
                .map(driver -> {
                    String carId = null;
                    if (driver.getCar() !=null)
                        carId = driver.getCar().getId();
                    return DriverDTO.builder()
                            .id(driver.getId())
                            .firstName(driver.getFirstName())
                            .lastName(driver.getLastName())
                            .birthDay(driver.getBirthDay().format(Utils.formatter))
                            .employedDate(driver.getEmployedDate().format(Utils.formatter))
                            .car(carId)
                            .build();
                }).collect(Collectors.toList());


    }
    public static Driver mapToDriver(DriverDTO driverDTO, EnterpriseRepo enterpriseRepo, CarRepo carRepo,Integer enterpriseId){
        Car car = null;
        if(driverDTO.getCar() != null)
            car = carRepo.getOne(driverDTO.getCar());
        return Driver.builder().id(driverDTO.getId())
                .firstName(driverDTO.getFirstName())
                .lastName(driverDTO.getLastName())
                .birthDay(LocalDate.parse(driverDTO.getBirthDay(),Utils.formatter))
                .employedDate(LocalDate.parse(driverDTO.getEmployedDate(),Utils.formatter))
                .car(car)
                .enterprise(enterpriseRepo.getOne(enterpriseId))
                .build();
    }



    public static DriverDTO mapToDriverDTO(Driver driver){
        String carId = null;
        if(driver.getCar() != null)
            carId = driver.getCar().getId();
        return DriverDTO.builder()
                .id(driver.getId())
                .firstName(driver.getFirstName())
                .lastName(driver.getLastName())
                .birthDay(driver.getBirthDay().format(Utils.formatter))
                .employedDate(driver.getEmployedDate().format(Utils.formatter))
                .car(carId)
                .build();
    }
}
