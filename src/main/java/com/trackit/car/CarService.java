package com.trackit.car;

import com.trackit.exception.*;

import java.util.List;
import java.util.Optional;

public interface CarService {

    List<CarDTO> findCarsByEnterpriseId(Integer id) throws EnterpriseNotFoundException;

    CarDTO  getCarByCarIdAndEntrepriseId(Integer enterpriseId, String carId) throws CarNotFoundForEntrepriseException, EnterpriseNotFoundException;

    CarDTO addCar(CarDTO car,Integer enterpriseId) throws CarAlreadyExistsException, DriverNotFoundException, EnterpriseNotFoundException;

    CarDTO updateCar(CarDTO car,Integer enterpriseId) throws CarsNotFoundException, DriverNotFoundException, EnterpriseNotFoundException;

    void deleteCarById(String id,Integer enterpriseId) throws CarsNotFoundException, EnterpriseNotFoundException, CarNotFoundForEntrepriseException;

}
