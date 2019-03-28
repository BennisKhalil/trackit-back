package com.trackit.enterprise;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnterpriseDTO {

    private Integer id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String address;


    private List<String> carsIds;

    private List<Integer> driverIds;

    @Min(0)
    private Integer nbCars;
}
