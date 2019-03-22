package com.trackit.car;

import java.util.List;

public interface CarService {

    List<Car> findCarsByEnterpriseId(Integer id);

    CarDTO addOrUpdateCar(CarDTO car);

    void deleteCarById(String id);

}
