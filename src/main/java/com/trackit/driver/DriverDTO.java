package com.trackit.driver;

import com.trackit.car.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DriverDTO {

    private Integer Id;

    private String firstName;

    private String lastName;

    private String birthDay;

    private String employedDate;

    private String car;

    private Integer enterprise;
}
