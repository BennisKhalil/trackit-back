package com.trackit.driver;


import com.trackit.car.Car;
import com.trackit.car.CarRepo;
import com.trackit.enterprise.Enterprise;
import com.trackit.enterprise.EnterpriseRepo;
import com.trackit.exception.CarsNotFoundException;
import com.trackit.exception.DriverAlreadyExistsException;
import com.trackit.exception.DriverNotFoundException;
import com.trackit.exception.EnterpriseNotFoundException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DriverServiceTest {

    @Mock
    DriverRepo driverRepo;

    @Mock
    EnterpriseRepo enterpriseRepo;

    @Mock
    CarRepo carRepo;

    @InjectMocks
    DriverServiceImpl driverService;


    @Test
    public void WhenEnterpriseNotFoundShouldThrowEnterpriseNotFoundException() {
        when(enterpriseRepo.existsById(1)).thenReturn(false);
        EnterpriseNotFoundException thrown = assertThrows(EnterpriseNotFoundException.class,
                () -> {
                    driverService.findAllDriversByEnterpriseId(1);
                });

        assertTrue(thrown.getMessage().equals("No Enterprise Found with the id 1"));

    }


    @Test
    public void WhenFetchingDriversByEnterpriseShouldReturnTheDrivers() throws EnterpriseNotFoundException {
        Enterprise enterprise = new Enterprise();
        enterprise.setId(1);
        enterprise.setName("TestEnterprise");


        List<Driver> mockedDrivers = new ArrayList<>();

        Driver driver1 = Driver.builder().Id(1)
                .firstName("driver1")
                .enterprise(enterprise)
                .birthDay(LocalDate.now())
                .employedDate(LocalDate.now())
                .car(Car.builder().Id("1").build())
                .build();

        Driver driver2 = Driver.builder().Id(2)
                .firstName("driver2")
                .enterprise(enterprise)
                .birthDay(LocalDate.now())
                .employedDate(LocalDate.now())
                .car(Car.builder().Id("2").build())
                .build();

        mockedDrivers.add(driver1);
        mockedDrivers.add(driver2);

        enterprise.setDrivers(mockedDrivers);
        when(enterpriseRepo.existsById(1)).thenReturn(true);
        when(driverRepo.findByEnterpriseId(1)).thenReturn(mockedDrivers);

        List<DriverDTO> drivers = driverService.findAllDriversByEnterpriseId(1);

        assertEquals(2, drivers.size());
        assertEquals(new Integer(1), drivers.get(0).getEnterprise());
        verify(enterpriseRepo, times(1)).existsById(1);
        verify(driverRepo, times(1)).findByEnterpriseId(1);
        verifyNoMoreInteractions(enterpriseRepo);
    }


    @Test
    public void WhenSaveDriverWithExistingIdShouldReturnCarAlreadyExistsException() {
        when(driverRepo.existsById(1)).thenReturn(true);
        DriverDTO driverDTO = DriverDTO.builder().Id(1).enterprise(1).build();
        DriverAlreadyExistsException thrown = Assertions.assertThrows(DriverAlreadyExistsException.class, () -> {
            driverService.addDriver(driverDTO);
        });
        assertEquals(thrown.getMessage(), "Driver with the Id " + driverDTO.getId() + " Already Exists");
    }

    @Test
    public void WhenSaveDriverWithInvalidCarIdShouldReturnCarAlreadyExistsException() {
        when(driverRepo.existsById(1)).thenReturn(false);
        when(carRepo.existsById("1")).thenReturn(false);
        DriverDTO driverDTO = DriverDTO.builder().Id(1).enterprise(1).car("1").build();
        CarsNotFoundException thrown = Assertions.assertThrows(CarsNotFoundException.class, () -> {
            driverService.addDriver(driverDTO);
        });
        assertEquals(thrown.getMessage(), "No Car Found with the Id " + driverDTO.getCar());
    }

    @Test
    public void WhenSaveDriverWithInvalidEnterpriseIdShouldReturnCarAlreadyExistsException() {
        when(driverRepo.existsById(1)).thenReturn(false);
        when(carRepo.existsById("1")).thenReturn(true);
        when(enterpriseRepo.existsById(1)).thenReturn(false);
        DriverDTO driverDTO = DriverDTO.builder().Id(1).enterprise(1).car("1").build();
        EnterpriseNotFoundException thrown = Assertions.assertThrows(EnterpriseNotFoundException.class, () -> {
            driverService.addDriver(driverDTO);
        });
        assertEquals(thrown.getMessage(), "No Enterprise Found with the Id " + driverDTO.getEnterprise());
    }


    @Test
    public void WhenSaveDriverShouldCallPersistMethod() throws CarsNotFoundException, EnterpriseNotFoundException, DriverAlreadyExistsException {
        when(driverRepo.existsById(1)).thenReturn(false);
        when(carRepo.existsById("1")).thenReturn(true);
        when(enterpriseRepo.existsById(1)).thenReturn(true);
        DriverDTO driverDTO = DriverDTO.builder()
                .Id(1).enterprise(1)
                .car("1")
                .firstName("Smith")
                .birthDay("12/08/2002")
                .employedDate("25/12/2009")
                .build();

        DriverDTO driverDTOResult = driverService.addDriver(driverDTO);

        verify(driverRepo, times(1)).save(any(Driver.class));
        assertEquals(driverDTOResult.getFirstName(), "Smith");

    }


    @Test
    public void WhenUpdateDriverWithInvalidIdShouldReturnCarAlreadyExistsException() {
        when(driverRepo.existsById(1)).thenReturn(false);
        DriverDTO driverDTO = DriverDTO.builder().Id(1).enterprise(1).build();
        DriverNotFoundException thrown = Assertions.assertThrows(DriverNotFoundException.class, () -> {
            driverService.updateDriver(driverDTO);
        });
        assertEquals(thrown.getMessage(), "No Driver Found with the Id " + driverDTO.getId());
    }

    @Test
    public void WhenUpdateCarWithInvalidCarIdShouldReturnCarAlreadyExistsException() {
        when(driverRepo.existsById(1)).thenReturn(true);
        when(carRepo.existsById("1")).thenReturn(false);
        DriverDTO driverDTO = DriverDTO.builder().Id(1).enterprise(1).car("1").build();
        CarsNotFoundException thrown = Assertions.assertThrows(CarsNotFoundException.class, () -> {
            driverService.updateDriver(driverDTO);
        });
        assertEquals(thrown.getMessage(), "No Car Found with the Id " + driverDTO.getCar());
    }

    @Test
    public void WhenUpdateCarWithInvalidEnterpriseIdShouldReturnCarAlreadyExistsException() {
        when(driverRepo.existsById(1)).thenReturn(true);
        when(carRepo.existsById("1")).thenReturn(true);
        when(enterpriseRepo.existsById(1)).thenReturn(false);
        DriverDTO driverDTO = DriverDTO.builder().Id(1).enterprise(1).car("1").build();
        EnterpriseNotFoundException thrown = Assertions.assertThrows(EnterpriseNotFoundException.class, () -> {
            driverService.updateDriver(driverDTO);
        });
        assertEquals(thrown.getMessage(), "No Enterprise Found with the Id " + driverDTO.getEnterprise());
    }


    @Test
    public void WhenUpdateCarShouldCallPersistMethod() throws CarsNotFoundException, EnterpriseNotFoundException, DriverNotFoundException {
        when(driverRepo.existsById(1)).thenReturn(true);
        when(carRepo.existsById("1")).thenReturn(true);
        when(enterpriseRepo.existsById(1)).thenReturn(true);

        DriverDTO driverDTO = DriverDTO.builder()
                .Id(1)
                .enterprise(1)
                .firstName("Smith")
                .car("1")
                .lastName("Doe")
                .birthDay("12/08/2002")
                .employedDate("06/12/2002")
                .build();

        driverService.updateDriver(driverDTO);

        driverDTO.setFirstName("Doe");

        DriverDTO driverDTOResult = driverService.updateDriver(driverDTO);

        verify(driverRepo, times(2)).save(any(Driver.class));
        assertEquals(driverDTOResult.getFirstName(), "Doe");

    }

    @Test
    public void WhenDeleteCarShouldCallDeleteMethod() {
        driverService.deleteDriver(1);
        verify(driverRepo, times(1)).deleteById(1);
    }

}
