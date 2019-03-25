package com.trackit.car;

import com.trackit.driver.Driver;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarDTO {


	private String Id;

	private String brand;

	private String model;

	private String trip;

	private String fuelConsumption;

	private Integer driver;
	
	private Integer enterprise;
}
