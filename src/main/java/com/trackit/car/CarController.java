package com.trackit.car;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trackit.exception.CarsNotFoundException;

@RestController 
@RequestMapping("/cars")
public class CarController {

	@Autowired
	private CarService carService;
	
	@GetMapping("/{enterpriseId}")
	private ResponseEntity<List<Car>> getCarsByEnterprise(@PathVariable Integer id) throws CarsNotFoundException {
		List<Car> cars =carService.findCarsByEnterpriseId(id);
		if(cars == null) {
			throw new CarsNotFoundException("no car found for this enterprise");
		}
		return new ResponseEntity<>(cars,HttpStatus.OK);
		
	}
}
