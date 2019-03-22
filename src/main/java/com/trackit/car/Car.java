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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Car {

	@Id
	private String Id;

	private String brand;

	private String model;

	private String trips;

	private String fuelConsumption;

	@OneToOne(cascade=CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name="driver_id")
	private Driver driver;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "enterprise_id")
	 private Enterprise enterprise;
	 
}
