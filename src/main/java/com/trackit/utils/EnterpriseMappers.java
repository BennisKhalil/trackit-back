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
        return EnterpriseDTO.builder()
                .id(enterprise.getId())
                .name(enterprise.getName())
                .address(enterprise.getAddress())
                .nbCars(enterprise.getNbCars())
                .build();
    }

    public static List<EnterpriseDTO> mapToEnterpriseDTOList(List<Enterprise> enterprises){

        return enterprises.stream()
                .map(enterprise -> {
                    return EnterpriseDTO.builder()
                            .id(enterprise.getId())
                            .name(enterprise.getName())
                            .address(enterprise.getAddress())
                            .nbCars(enterprise.getNbCars())
                            .build();
                }).collect(Collectors.toList());
    }

    public static Enterprise maptoEnterprise(EnterpriseDTO enterpriseDTO, CarRepo carRepo, DriverRepo driverRepo){

        return Enterprise.builder()
                .id(enterpriseDTO.getId())
                .name(enterpriseDTO.getName())
                .address(enterpriseDTO.getAddress())
                .nbCars(enterpriseDTO.getNbCars())
                .build();
    }
}
