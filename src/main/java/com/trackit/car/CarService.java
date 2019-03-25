package com.trackit.car;

import com.trackit.exception.EnterpriseNotFoundException;

import java.util.List;

public interface CarService {

    List<CarDTO> findCarsByEnterpriseId(Integer id) throws EnterpriseNotFoundException;

    CarDTO addOrUpdateCar(CarDTO car);

    void deleteCarById(String id);

}
