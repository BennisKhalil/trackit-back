package com.trackit.car;

import lombok.Data;

@Data
public class CarDTO {

	private String Id;

	private String brand;

	private String model;

	private String trips;

	private String fuelConsumption;
	
	private Integer enterprise;
}
