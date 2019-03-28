package com.trackit.driver;

import com.trackit.car.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DriverDTO {

    private Integer id;
    @NotNull
    @NotEmpty
    private String firstName;
    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    private String birthDay;

    @NotNull
    @NotEmpty
    private String employedDate;

    private String car;

    @NotNull
    private Integer enterprise;
}
