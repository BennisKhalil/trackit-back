package com.trackit.car;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import com.trackit.exception.*;
import com.trackit.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/enterprises")
public class CarController {

	@Autowired
	private CarService carService;

	@GetMapping("/{id}/cars")
	private ResponseEntity<CarMessage> getCarsByEnterprise(@PathVariable Integer id) throws EnterpriseNotFoundException {
		List<CarDTO> cars = carService.findCarsByEnterpriseId(id);
		CarMessage carMessage = CarMessage.builder()
				.date(LocalDateTime.now().format(Utils.LocalDateTimeFormatter))
				.path("/enterprise/"+id+"/cars")
				.cars(cars)
				.build();
		return new ResponseEntity<>(carMessage, HttpStatus.OK);
	}

	@GetMapping("/{id_enterprise}/cars/{id_car}")
	private ResponseEntity<CarMessage> getCarById(@PathVariable("id_enterprise") Integer idEnterprise,@PathVariable("id_car") String idCar) throws EnterpriseNotFoundException, CarNotFoundForEntrepriseException {
		CarDTO car = carService.getCarByCarIdAndEntrepriseId(idEnterprise,idCar);
		CarMessage carMessage = CarMessage.builder()
				.date(LocalDateTime.now().format(Utils.LocalDateTimeFormatter))
				.path("/enterprise/"+idEnterprise+"/cars/"+idCar)
				.cars(Collections.singletonList(car))
				.build();
		return new ResponseEntity<>(carMessage, HttpStatus.OK);
	}

	@PostMapping("/{id_enterprise}/cars")
	private ResponseEntity<CarMessage> addCarToEnterprise(@Valid @RequestBody CarDTO carDTO, @PathVariable("id_enterprise") Integer enterpriseId) throws EnterpriseNotFoundException, CarAlreadyExistsException, DriverNotFoundException {
		CarDTO car = carService.addCar(carDTO,enterpriseId);
		CarMessage carMessage = CarMessage.builder()
				.date(LocalDateTime.now().format(Utils.LocalDateTimeFormatter))
				.path("/enterprise/"+enterpriseId+"/cars")
				.cars(Collections.singletonList(car))
				.build();
		return new ResponseEntity<>(carMessage, HttpStatus.CREATED);
	}

	@PutMapping("/{id_enterprise}/cars/{id_car}")
	private ResponseEntity<CarMessage> updateCar(@Valid @RequestBody CarDTO carDTO,@PathVariable("id_enterprise") Integer enterpriseId,@PathVariable("id_car") String carId) throws EnterpriseNotFoundException, DriverNotFoundException, CarsNotFoundException {
		carDTO.setId(carId);
		CarDTO car = carService.updateCar(carDTO, enterpriseId);
		CarMessage carMessage = CarMessage.builder()
				.date(LocalDateTime.now().format(Utils.LocalDateTimeFormatter))
				.path("/enterprise/"+enterpriseId+"/cars"+carId)
				.cars(Collections.singletonList(car))
				.build();
		return new ResponseEntity<>(carMessage, HttpStatus.OK);
	}
	@DeleteMapping("/{id_enterprise}/cars/{id_car}")
	private ResponseEntity<CarMessage> deleteCar(@PathVariable("id_car") String carId, @PathVariable("id_enterprise") Integer enterpriseId) throws CarsNotFoundException, CarNotFoundForEntrepriseException, EnterpriseNotFoundException {
		carService.deleteCarById(carId,enterpriseId);
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}
}