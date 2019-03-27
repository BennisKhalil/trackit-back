package com.trackit.driver;

import java.time.LocalDate;

import javax.persistence.*;

import com.trackit.car.Car;

import com.trackit.enterprise.Enterprise;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {

	@Id
	@GeneratedValue
	private Integer Id;

	private String firstName;

	private String lastName;

	private LocalDate birthDay;

	private LocalDate employedDate;

	@OneToOne(mappedBy= "driver",fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, optional = false)
	private Car car;

	@ManyToOne
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;

}
