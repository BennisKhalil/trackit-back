package com.trackit;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.trackit.car.Car;
import com.trackit.car.CarRepo;
import com.trackit.car.CarServiceImpl;
import com.trackit.enterprise.Enterprise;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {

	@Mock 
	CarRepo carRepo;
	
	@InjectMocks
	CarServiceImpl carService;
	
	@Test
	public void WhenFetchingCarsByEnterpriseShouldReturnTheCars(){
		Enterprise enterprise = new Enterprise ();
		enterprise.setId(1);
		List<Car> mockedcars = new ArrayList<>();
		Car car1 = new Car();
		car1.setBrand("ford");
		Car car2 = new Car();
		car2.setBrand("lexus");
		mockedcars.add(car1);
		mockedcars.add(car2);
		when(carRepo.findByEnterpriseId(1)).thenReturn(mockedcars);
		
		List<Car> cars = carService.findCarsByEnterpriseId(1);
		assertThat(cars,hasItem(car1));
		assertThat(cars,hasItem(car2));
		
	}
	
}
