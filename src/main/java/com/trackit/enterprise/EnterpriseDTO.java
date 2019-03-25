package com.trackit.enterprise;

import lombok.*;

import java.util.List;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnterpriseDTO {

    private Integer id;

    private String name;

    private String address;

    private List<String> carsIds;

    private List<Integer> driverIds;

    private String nbCars;
}
