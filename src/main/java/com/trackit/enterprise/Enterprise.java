package com.trackit.enterprise;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Formula;

import com.trackit.car.Car;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Enterprise {

	@Id
	@GeneratedValue
	private Integer id;

	private String name;

	private String address;

	@OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
	private List<Car> cars;
	
	@Formula("(select count(*) from car c where c.enterprise_id = id)")
	private String nbCars;

	
	

}
