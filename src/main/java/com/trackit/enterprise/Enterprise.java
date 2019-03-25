package com.trackit.enterprise;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.trackit.driver.Driver;
import lombok.*;
import org.hibernate.annotations.Formula;

import com.trackit.car.Car;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enterprise {

	@Id
	@GeneratedValue
	private Integer id;

	private String name;

	private String address;

	@OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
	private List<Car> cars;

	@OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
	private List<Driver> drivers;
	
	@Formula("(select count(*) from car c where c.enterprise_id = id)")
	private String nbCars;

	
	

}
