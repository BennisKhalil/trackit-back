package com.trackit.car;

import com.trackit.driver.Driver;
import com.trackit.driver.DriverRepo;
import com.trackit.enterprise.Enterprise;
import com.trackit.enterprise.EnterpriseRepo;
import com.trackit.exception.CarAlreadyExistsException;
import com.trackit.exception.CarsNotFoundException;
import com.trackit.exception.DriverNotFoundException;
import com.trackit.exception.EnterpriseNotFoundException;
import net.bytebuddy.asm.Advice;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {

    @Mock
    CarRepo carRepo;

    @Mock
    EnterpriseRepo enterpriseRepo;

    @Mock
    DriverRepo driverRepo;

    @InjectMocks
    CarServiceImpl carService;


    @Test
    public void WhenFetchingAllCarsWithInvalidEnterpriseIdShouldThrowEnterpriseNotFoundException() {
        when(enterpriseRepo.existsById(1)).thenReturn(false);
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
        when(enterpriseRepo.existsById(1)).thenReturn(true);

        List<CarDTO> cars = carService.findCarsByEnterpriseId(1);

        assertEquals(2, cars.size());
        assertEquals(new Integer(1), cars.get(0).getEnterprise());
        verify(carRepo, times(1)).findByEnterpriseId(1);
        verifyNoMoreInteractions(carRepo);
    }

    @Test
    public void WhenFetchingCarWithInvalidIdShouldThrowCarNotFoundException(){
        when(carRepo.existsById("1")).thenReturn(false);
        CarsNotFoundException thrown =  Assertions.assertThrows(CarsNotFoundException.class,()->{carService.getCar("1");});
        assertEquals(thrown.getMessage(), "No Car Found with the Id "+1);
    }

    @Test
    public void WhenFetchingCarWithValidIdShouldReturnCar() throws CarsNotFoundException {
        when(carRepo.existsById("1")).thenReturn(true);
        Enterprise enterprise = Enterprise.builder().id(1).build();
        when(carRepo.getOne("1")).thenReturn(Car.builder().id("1").enterprise(enterprise).build());

        CarDTO carDTO = carService.getCar("1");
        verify(carRepo, times(1)).getOne("1");
        assertEquals(carDTO.getId(), "1");


    }


    @Test
    public void WhenSavingCarThatAlreadyExistsShouldThrowCarAlreadyExistsException() {
        when(carRepo.existsById("1")).thenReturn(true);
        CarAlreadyExistsException thrown = Assertions.assertThrows(CarAlreadyExistsException.class, () -> {
            carService.addCar(CarDTO.builder().id("1").build());
        });
        assertEquals(thrown.getMessage(), "Car with the Id 1 Already Exists");
    }

    @Test
    public void WhenSavingCarWithInvalidDriverIdShouldThrowDriverNotFoundException() {
        when(carRepo.existsById("1")).thenReturn(false);
        when(driverRepo.existsById(1)).thenReturn(false);
        DriverNotFoundException thrown = Assertions.assertThrows(DriverNotFoundException.class, () -> {
            carService.addCar(CarDTO.builder().id("1").driver(1).build());
        });
        assertEquals(thrown.getMessage(), "No Driver Found with the Id 1");
    }

    @Test
    public void WhenSavingCarWithInvalidEnterpriseIdShouldThrowEnterpriseNotFoundException() {
        when(carRepo.existsById("1")).thenReturn(false);
        when(driverRepo.existsById(1)).thenReturn(true);
        when(enterpriseRepo.existsById(1)).thenReturn(false);
        EnterpriseNotFoundException thrown = Assertions.assertThrows(EnterpriseNotFoundException.class, () -> {
            carService.addCar(CarDTO.builder().id("1").driver(1).enterprise(1).build());
        });
        assertEquals(thrown.getMessage(), "No Enterprise Found with the Id 1");

    }

    @Test
    public void WhenSavingCarShouldCallPersistMethod() throws Throwable {
        when(carRepo.existsById("1")).thenReturn(false);
        when(driverRepo.existsById(1)).thenReturn(true);
        when(enterpriseRepo.existsById(1)).thenReturn(true);
        CarDTO carDTO = CarDTO.builder()
                .id("1")
                .driver(1)
                .enterprise(1)
                .build();

        Car carEntity = carService.mapToCar(carDTO);
        carEntity.setEnterprise(Enterprise.builder().id(1).build());
        when(carRepo.getOne("1")).thenReturn(carEntity);


        carService.addCar(carDTO);
        verify(carRepo, times(1)).save(any(Car.class));
        assertEquals(carDTO.getId(), carEntity.getId());

    }

    @Test
    public void WhenUpdatingCarThatAlreadyExistsShouldThrowCarAlreadyExistsException() {
        when(carRepo.existsById("1")).thenReturn(false);
        CarsNotFoundException thrown = Assertions.assertThrows(CarsNotFoundException.class, () -> {
            carService.updateCar(CarDTO.builder().id("1").build());
        });
        assertEquals(thrown.getMessage(), "No Car Found with the Id 1");
    }

    @Test
    public void WhenUpdatingCarWithInvalidDriverIdShouldThrowDriverNotFoundException() {
        when(carRepo.existsById("1")).thenReturn(true);
        when(driverRepo.existsById(1)).thenReturn(false);
        DriverNotFoundException thrown = Assertions.assertThrows(DriverNotFoundException.class, () -> {
            carService.updateCar(CarDTO.builder().id("1").driver(1).build());
        });
        assertEquals(thrown.getMessage(), "No Driver Found with the Id 1");
    }

    @Test
    public void WhenUpdatingCarWithInvalidEnterpriseIdShouldThrowEnterpriseNotFoundException() {
        when(carRepo.existsById("1")).thenReturn(true);
        when(driverRepo.existsById(1)).thenReturn(true);
        when(enterpriseRepo.existsById(1)).thenReturn(false);
        EnterpriseNotFoundException thrown = Assertions.assertThrows(EnterpriseNotFoundException.class, () -> {
            carService.updateCar(CarDTO.builder().id("1").driver(1).enterprise(1).build());
        });
        assertEquals(thrown.getMessage(), "No Enterprise Found with the Id 1");

    }


    @Test
    public void WhenUpdateCarShouldCallPersistMethod() throws DriverNotFoundException, CarsNotFoundException, EnterpriseNotFoundException, CarAlreadyExistsException {
        when(carRepo.existsById("1")).thenReturn(true);
        when(driverRepo.existsById(1)).thenReturn(true);
        when(enterpriseRepo.existsById(1)).thenReturn(true);
        CarDTO carDTO = CarDTO.builder()
                .id("1")
                .driver(1)
                .enterprise(1)
                .build();

        Car carEntity = carService.mapToCar(carDTO);
        carEntity.setEnterprise(Enterprise.builder().id(1).build());
        when(carRepo.getOne("1")).thenReturn(carEntity);
        when(driverRepo.getOne(1)).thenReturn(Driver.builder().id(1).build());

        carDTO.setBrand("volvo");
        CarDTO carDTOResult = carService.updateCar(carDTO);
        verify(carRepo, times(1)).save(any(Car.class));


    }

    @Test
    public void WhenDeleteCarShouldCallDeleteMethod() {
        carService.deleteCarById("1");
        verify(carRepo, times(1)).deleteById("1");
    }

}
