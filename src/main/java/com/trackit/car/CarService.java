package com.trackit.car;

import com.trackit.exception.CarAlreadyExistsException;
import com.trackit.exception.CarsNotFoundException;
import com.trackit.exception.EnterpriseNotFoundException;
import com.trackit.exception.DriverNotFoundException;

import java.util.List;

public interface CarService {

    List<CarDTO> findCarsByEnterpriseId(Integer id) throws EnterpriseNotFoundException;

    CarDTO addCar(CarDTO car) throws CarAlreadyExistsException, DriverNotFoundException, EnterpriseNotFoundException;

    CarDTO updateCar(CarDTO car) throws CarsNotFoundException, DriverNotFoundException, EnterpriseNotFoundException;

    void deleteCarById(String id);

}
