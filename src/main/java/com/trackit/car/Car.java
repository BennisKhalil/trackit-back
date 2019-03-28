package com.trackit.car;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.trackit.driver.Driver;
import com.trackit.enterprise.Enterprise;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {

	@Id
	private String id;

	private String brand;

	private String model;

	private String nextTrip;

	private String fuelConsumption;

	@OneToOne(cascade=CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name="driver_id")
	private Driver driver;

	@ManyToOne
	@JoinColumn(name = "enterprise_id")
	 private Enterprise enterprise;

}
