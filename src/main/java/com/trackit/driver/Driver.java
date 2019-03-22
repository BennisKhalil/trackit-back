package com.trackit.driver;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.trackit.car.Car;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Driver {

	@Id
	@GeneratedValue
	private Integer Id;

	private String name;

	private String lastName;

	private LocalDate birthDay;

	private LocalDate employedDate;

	@OneToOne(mappedBy= "com/trackit/driver",fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, optional = false)
	private Car car;

}
