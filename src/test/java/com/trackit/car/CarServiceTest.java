package com.trackit.car;

import com.trackit.driver.Driver;
import com.trackit.driver.DriverRepo;
import com.trackit.enterprise.Enterprise;
import com.trackit.enterprise.EnterpriseRepo;
import com.trackit.exception.*;
import javafx.beans.binding.When;
import net.bytebuddy.asm.Advice;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.trackit.utils.CarMappers.*;
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
        verify(carRepo, times(1)).findByEnterpriseId(1);
        verifyNoMoreInteractions(carRepo);
    }

    @Test
    public void WhenFetchingCarForEnterpriseWithInvalidEnterpriseIdShouldThrowEnterpriseNotFoundException(){
        when(enterpriseRepo.existsById(1)).thenReturn(false);

        EnterpriseNotFoundException thrown = Assertions.assertThrows(EnterpriseNotFoundException.class,()->{carService.getCarByCarIdAndEntrepriseId(1,"1");});
        assertEquals(thrown.getMessage(),"No Enterprise Found with the Id 1");
    }

    @Test
    public void WhenFetchingCarForEnterpriseWithInvalidIdShouldThrowCarNotFoundForEntrepriseException(){
        when(enterpriseRepo.existsById(1)).thenReturn(true);
        when(carRepo.findByIdAndEnterpriseId("1",1)).thenReturn(Optional.empty());
        CarNotFoundForEntrepriseException thrown = Assertions.assertThrows(CarNotFoundForEntrepriseException.class,()->{carService.getCarByCarIdAndEntrepriseId(1,"1");});
        assertEquals(thrown.getMessage(),"no car with the id "+1+" is found in this enterprise");
    }

    @Test
    public void WhenFetchingCarForEnterpriseWithValidIdShouldReturnCar() throws CarNotFoundForEntrepriseException, EnterpriseNotFoundException {

        when(enterpriseRepo.existsById(1)).thenReturn(true);
        Enterprise enterprise = Enterprise.builder().id(1).build();
        Car car = Car.builder().id("1").enterprise(enterprise).build();
        when(carRepo.findByIdAndEnterpriseId("1",1)).thenReturn(Optional.of(car));
        CarDTO carDTO = carService.getCarByCarIdAndEntrepriseId(1,"1");

        verify(carRepo,times(1)).findByIdAndEnterpriseId("1",1);
        assertEquals(carDTO.getId(),"1");
    }


    @Test
    public void WhenSavingCarThatAlreadyExistsShouldThrowCarAlreadyExistsException() {
        when(carRepo.existsById("1")).thenReturn(true);
        CarAlreadyExistsException thrown = Assertions.assertThrows(CarAlreadyExistsException.class, () -> {
            carService.addCar(CarDTO.builder().id("1").build(),1);
        });
        assertEquals(thrown.getMessage(), "Car with the Id 1 Already Exists");
    }

    @Test
    public void WhenSavingCarWithInvalidDriverIdShouldThrowDriverNotFoundException() {
        when(carRepo.existsById("1")).thenReturn(false);
        when(driverRepo.existsById(1)).thenReturn(false);
        DriverNotFoundException thrown = Assertions.assertThrows(DriverNotFoundException.class, () -> {
            carService.addCar(CarDTO.builder().id("1").driver(1).build(),1);
        });
        assertEquals(thrown.getMessage(), "No Driver Found with the Id 1");
    }

    @Test
    public void WhenSavingCarWithInvalidEnterpriseIdShouldThrowEnterpriseNotFoundException() {
        when(carRepo.existsById("1")).thenReturn(false);
        when(driverRepo.existsById(1)).thenReturn(true);
        when(enterpriseRepo.existsById(1)).thenReturn(false);
        EnterpriseNotFoundException thrown = Assertions.assertThrows(EnterpriseNotFoundException.class, () -> {
            carService.addCar(CarDTO.builder().id("1").driver(1).build(),1);
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
                .build();

        Car carEntity = mapToCar(carDTO, enterpriseRepo, driverRepo, 1);
        carEntity.setEnterprise(Enterprise.builder().id(1).build());
        when(carRepo.getOne("1")).thenReturn(carEntity);


        carService.addCar(carDTO, 1);
        verify(carRepo, times(1)).save(any(Car.class));
        assertEquals(carDTO.getId(), carEntity.getId());

    }

    @Test
    public void WhenUpdatingCarThatAlreadyExistsShouldThrowCarAlreadyExistsException() {
        when(carRepo.existsById("1")).thenReturn(false);
        CarsNotFoundException thrown = Assertions.assertThrows(CarsNotFoundException.class, () -> {
            carService.updateCar(CarDTO.builder().id("1").build(), 1);
        });
        assertEquals(thrown.getMessage(), "No Car Found with the Id 1");
    }

    @Test
    public void WhenUpdatingCarWithInvalidDriverIdShouldThrowDriverNotFoundException() {
        when(carRepo.existsById("1")).thenReturn(true);
        when(driverRepo.existsById(1)).thenReturn(false);
        DriverNotFoundException thrown = Assertions.assertThrows(DriverNotFoundException.class, () -> {
            carService.updateCar(CarDTO.builder().id("1").driver(1).build(), 1);
        });
        assertEquals(thrown.getMessage(), "No Driver Found with the Id 1");
    }

    @Test
    public void WhenUpdatingCarWithInvalidEnterpriseIdShouldThrowEnterpriseNotFoundException() {
        when(carRepo.existsById("1")).thenReturn(true);
        when(driverRepo.existsById(1)).thenReturn(true);
        when(enterpriseRepo.existsById(1)).thenReturn(false);
        EnterpriseNotFoundException thrown = Assertions.assertThrows(EnterpriseNotFoundException.class, () -> {
            carService.updateCar(CarDTO.builder().id("1").driver(1).build(), 1);
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
                .build();

        Car carEntity = mapToCar(carDTO, enterpriseRepo, driverRepo,1);
        when(carRepo.getOne("1")).thenReturn(carEntity);

        carEntity.setEnterprise(Enterprise.builder().id(1).build());

        carDTO.setBrand("volvo");
        CarDTO carDTOResult = carService.updateCar(carDTO, 1);
        verify(carRepo, times(1)).save(any(Car.class));


    }

    @Test
    public void WhenDeletingCarWithInvalidEnterpriseIdShouldThrowEnterpriseNotFoundException(){
        when(enterpriseRepo.existsById(1)).thenReturn(false);
        EnterpriseNotFoundException thrown = Assertions.assertThrows(EnterpriseNotFoundException.class,()->{carService.deleteCarById("1",1);});
        assertEquals(thrown.getMessage(), "No Enterprise Found with the Id "+1);
    }


    @Test
    public void WhenDeletingCarThatDoesntBelongToEnterpriseShouldThrowCarNotFoundException(){
        when(enterpriseRepo.existsById(1)).thenReturn(true);
        when(carRepo.findByIdAndEnterpriseId("1",1)).thenReturn(Optional.empty());
        CarNotFoundForEntrepriseException thrown = Assertions.assertThrows(CarNotFoundForEntrepriseException.class,()->{carService.deleteCarById("1",1);});
        assertEquals(thrown.getMessage(), "No Car Found with the Id "+1);
    }
    @Test
    public void WhenDeleteCarShouldCallDeleteMethod() throws CarsNotFoundException, CarNotFoundForEntrepriseException, EnterpriseNotFoundException {
        when(enterpriseRepo.existsById(1)).thenReturn(true);
        when(carRepo.findByIdAndEnterpriseId("1",1)).thenReturn(Optional.of(Car.builder().id("1").build()));
        carService.deleteCarById("1",1);
        verify(carRepo, times(1)).deleteById("1");
    }



}
