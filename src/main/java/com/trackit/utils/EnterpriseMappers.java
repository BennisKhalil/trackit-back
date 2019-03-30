package com.trackit.utils;

import com.trackit.car.Car;
import com.trackit.car.CarRepo;
import com.trackit.driver.Driver;
import com.trackit.driver.DriverRepo;
import com.trackit.enterprise.Enterprise;
import com.trackit.enterprise.EnterpriseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class EnterpriseMappers {
    public static EnterpriseDTO mapToEnterpriseDTO(Enterprise enterprise){
        List<Car> cars = enterprise.getCars();
        List<Driver> drivers = enterprise.getDrivers();
        List<String> carsIds = null;
        List<Integer> driverIds = null;

        if (cars != null) {
            carsIds = cars.parallelStream()
                    .map(car -> car.getId())
                    .collect(Collectors.toList());
        }
        if (drivers != null) {
            driverIds = drivers.stream()
                    .map(driver -> driver.getId())
                    .collect(Collectors.toList());
            System.out.println(drivers);
        }

        return EnterpriseDTO.builder()
                .id(enterprise.getId())
                .name(enterprise.getName())
                .address(enterprise.getAddress())
                .carsIds(carsIds)
                .driverIds(driverIds)
                .nbCars(enterprise.getNbCars())
                .build();
    }

    public static List<EnterpriseDTO> mapToEnterpriseDTOList(List<Enterprise> enterprises){

        return enterprises.stream()
                .map(enterprise -> {
                    List<Car> cars = enterprise.getCars();
                    List<Driver> drivers = enterprise.getDrivers();

                    List<String> cardIds = null;
                    List<Integer> driverIds = null;

                    if(cars != null){
                        cardIds = cars
                                .stream()
                                .map(Car::getId)
                                .collect(Collectors.toList());
                    }
                    if(drivers !=null){
                        driverIds = drivers
                                .stream()
                                .map(Driver::getId)
                                .collect(Collectors.toList());
                    }

                    return EnterpriseDTO.builder()
                            .id(enterprise.getId())
                            .name(enterprise.getName())
                            .address(enterprise.getAddress())
                            .carsIds(cardIds)
                            .driverIds(driverIds)
                            .nbCars(enterprise.getNbCars())
                            .build();
                }).collect(Collectors.toList());
    }

    public static Enterprise maptoEnterprise(EnterpriseDTO enterpriseDTO, CarRepo carRepo, DriverRepo driverRepo){
        List<String> carsIds = enterpriseDTO.getCarsIds();
        List<Integer> driverIds = enterpriseDTO.getDriverIds();
        List<Car> cars = null;
        List<Driver> drivers = null;
        if (carsIds != null) {
            cars = carsIds.parallelStream()
                    .map(id -> carRepo.findById(id).get())
                    .collect(Collectors.toList());
        }
        if (driverIds != null) {
            drivers = driverIds.parallelStream()
                    .map(id -> driverRepo.findById(id).get())
                    .collect(Collectors.toList());
        }

        return Enterprise.builder()
                .id(enterpriseDTO.getId())
                .name(enterpriseDTO.getName())
                .address(enterpriseDTO.getAddress())
                .cars(cars)
                .drivers(drivers)
                .nbCars(enterpriseDTO.getNbCars())
                .build();
    }
}
