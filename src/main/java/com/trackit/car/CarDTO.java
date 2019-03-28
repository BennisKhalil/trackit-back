package com.trackit.car;

import com.trackit.driver.Driver;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarDTO {


	@NotNull
	@NotEmpty
	private String id;

	@NotNull
	@NotEmpty
	private String brand;

	@NotNull
	@NotEmpty
	private String model;

	private String trip;

	private String fuelConsumption;

	private Integer driver;

	@NotNull
	private Integer enterprise;
}
