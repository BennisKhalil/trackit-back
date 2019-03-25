package com.trackit.car;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.trackit.car.*;
import com.trackit.enterprise.Enterprise;
import com.trackit.enterprise.EnterpriseRepo;
import com.trackit.exception.EnterpriseNotFoundException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {

    @Mock
    CarRepo carRepo;

    @Mock
    EnterpriseRepo enterpriseRepo;

    @InjectMocks
    CarServiceImpl carService;


    @Test
    public void WhenFetchingAllCarsWithInvalidEnterpriseIdShouldThrowEnterpriseNotFoundException() {
        when(enterpriseRepo.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(EnterpriseNotFoundException.class, () -> {
            carService.findCarsByEnterpriseId(1);
        }, "No Enterprise Found with the Id 1");
    }

    @Test
    public void WhenFetchingCarsByEnterpriseShouldReturnTheCars() throws EnterpriseNotFoundException {
        Enterprise enterprise = new Enterprise();
        enterprise.setId(1);
        enterprise.setName("TestEnterprise");


        List<Car> mockedcars = new ArrayList<>();

        Car car1 = new Car();
        car1.setBrand("ford");
        car1.setEnterprise(enterprise);

        Car car2 = new Car();
        car2.setBrand("lexus");
        car2.setEnterprise(enterprise);

        mockedcars.add(car1);
        mockedcars.add(car2);

        when(carRepo.findByEnterpriseId(1)).thenReturn(mockedcars);
        when(enterpriseRepo.findById(1)).thenReturn(Optional.of(enterprise));

        List<CarDTO> cars = carService.findCarsByEnterpriseId(1);

        assertEquals(2, cars.size());
        assertEquals(new Integer(1), cars.get(0).getEnterprise());
        verify(carRepo, times(1)).findByEnterpriseId(1);
    }


    @Test
    public void WhenSavingCarShouldCallPersistMethod() {
        CarDTO carDTO = new CarDTO();
        carDTO.setId("1");
        carDTO.setBrand("ford");
        Car carEntity = carService.mapToCar(carDTO);
        carService.addOrUpdateCar(carDTO);
        verify(carRepo, times(1)).save(any(Car.class));
        assertEquals(carDTO.getId(), carEntity.getId());

    }

    @Test
    public void WhenUpdateCarShouldCallPersistMethod() {
        CarDTO carDTO = new CarDTO();
        carDTO.setId("1");
        carDTO.setBrand("ford");

        carService.addOrUpdateCar(carDTO);

        carDTO.setBrand("volvo");

        CarDTO carDTOResult = carService.addOrUpdateCar(carDTO);

        verify(carRepo, times(2)).save(any(Car.class));
        assertEquals(carDTOResult.getBrand(), "volvo");

    }

    @Test
    public void WhenDeleteCarShouldCallDeleteMethod() {
        carService.deleteCarById("1");
        verify(carRepo, times(1)).deleteById("1");
    }

}