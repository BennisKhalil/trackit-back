package com.trackit.car;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import com.trackit.exception.CarAlreadyExistsException;
import com.trackit.exception.DriverNotFoundException;
import com.trackit.exception.EnterpriseNotFoundException;
import com.trackit.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.trackit.exception.CarsNotFoundException;

import javax.validation.Valid;

@RestController
@RequestMapping("/cars")
public class CarController {

	@Autowired
	private CarService carService;

	@GetMapping("enterprise/{id}")
	private ResponseEntity<CarMessage> getCarsByEnterprise(@PathVariable Integer id) throws EnterpriseNotFoundException {
		List<CarDTO> cars = carService.findCarsByEnterpriseId(id);
		CarMessage carMessage = CarMessage.builder()
				.date(LocalDateTime.now().format(Utils.LocalDateTimeFormatter))
				.path("/cars/enterprises/"+id)
				.cars(cars)
				.build();
		return new ResponseEntity<>(carMessage, HttpStatus.OK);
	}

	@PostMapping
	private ResponseEntity<CarMessage> addCar(@Valid @RequestBody CarDTO carDTO) throws EnterpriseNotFoundException, CarAlreadyExistsException, DriverNotFoundException {
		CarDTO car = carService.addCar(carDTO);
		CarMessage carMessage = CarMessage.builder()
				.date(LocalDateTime.now().format(Utils.LocalDateTimeFormatter))
				.path("/cars")
				.cars(Collections.singletonList(car))
				.build();
		return new ResponseEntity<>(carMessage, HttpStatus.CREATED);
	}

	@PutMapping
	private ResponseEntity<CarMessage> updateCar(@Valid @RequestBody CarDTO carDTO) throws EnterpriseNotFoundException, DriverNotFoundException, CarsNotFoundException {
		CarDTO car = carService.updateCar(carDTO);
		CarMessage carMessage = CarMessage.builder()
				.date(LocalDateTime.now().format(Utils.LocalDateTimeFormatter))
				.path("/cars")
				.cars(Collections.singletonList(car))
				.build();
		return new ResponseEntity<>(carMessage, HttpStatus.OK);
	}
	@DeleteMapping("/{id}")
	private ResponseEntity<CarMessage> deleteCar(@PathVariable String id) {
		carService.deleteCarById(id);
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}
}