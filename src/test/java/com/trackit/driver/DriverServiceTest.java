package com.trackit.driver;


import com.trackit.car.Car;
import com.trackit.car.CarRepo;
import com.trackit.driver.*;
import com.trackit.enterprise.Enterprise;
import com.trackit.enterprise.EnterpriseRepo;
import com.trackit.exception.CarsNotFoundException;
import com.trackit.exception.EnterpriseNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.Assert.assertEquals;
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
        when(enterpriseRepo.findById(1)).thenReturn(Optional.empty());
        EnterpriseNotFoundException thrown =assertThrows(EnterpriseNotFoundException.class,
                ()-> {driverService.findAllDriversByEnterpriseId(1);});

        assertTrue(thrown.getMessage().equals("No Enterprise Found with the id 1"));

    }



    @Test
    public void WhenUpdateOrSaveWithoutValidCarIdShouldReturnNoCarFoundException(){
        DriverDTO driverDTO = DriverDTO.builder()
                .enterprise(1)
                .firstName("khalil")
                .car("2")
                .lastName("bennis")
                .birthDay("12/08/2002")
                .employedDate("06/12/2002")
                .build();

        when(carRepo.findById("2")).thenReturn(Optional.empty());


        CarsNotFoundException thrown = assertThrows(CarsNotFoundException.class, ()-> {driverService.addOrUpdateDriver(driverDTO);});
        assertEquals(thrown.getMessage(), "No Car Found with the Id 2");
    }

    @Test
    public void WhenUpdateOrSaveWithoutValidEnterpriseIdShouldReturnNoCarFoundException(){
        DriverDTO driverDTO = DriverDTO.builder()
                .enterprise(1)
                .firstName("khalil")
                .car("2")
                .lastName("bennis")
                .birthDay("12/08/2002")
                .employedDate("06/12/2002")
                .build();

        when(enterpriseRepo.findById(1)).thenReturn(Optional.empty());
        when(carRepo.findById("2")).thenReturn(Optional.of(new Car()));

        EnterpriseNotFoundException thrown = assertThrows(EnterpriseNotFoundException.class, ()-> {driverService.addOrUpdateDriver(driverDTO);});
        assertEquals(thrown.getMessage(), "No Enterprise Found with the Id 1");
    }

    @Test
    public void WhenFetchingDriversByEnterpriseShouldReturnTheDrivers() throws EnterpriseNotFoundException {
        Enterprise enterprise = new Enterprise();
        enterprise.setId(1);
        enterprise.setName("TestEnterprise");


        List<Driver> mockedDrivers = new ArrayList<>();

        Driver driver1 = new Driver();
        driver1.setId(1);
        driver1.setFirstName("driver1");
        driver1.setEnterprise(enterprise);
        driver1.setBirthDay(LocalDate.now());
        driver1.setEmployedDate(LocalDate.now());
        driver1.setCar(Car.builder().Id("1").build());

        Driver driver2 = new Driver();
        driver2.setId(2);
        driver2.setFirstName("driver2");
        driver2.setEnterprise(enterprise);
        driver2.setBirthDay(LocalDate.now());
        driver2.setEmployedDate(LocalDate.now());
        driver2.setCar(Car.builder().Id("2").build());

        mockedDrivers.add(driver1);
        mockedDrivers.add(driver2);

        enterprise.setDrivers(mockedDrivers);
        when(enterpriseRepo.findById(1)).thenReturn(Optional.of(enterprise));
        when(driverRepo.findByEnteprise(enterprise)).thenReturn(mockedDrivers);

        List<DriverDTO> drivers = driverService.findAllDriversByEnterpriseId(1);

        assertEquals(2, drivers.size());
        assertEquals(new Integer(1), drivers.get(0).getEnterprise());
        verify(enterpriseRepo, times(1)).findById(1);
        verify(driverRepo, times(1)).findByEnteprise(enterprise);
    }


    @Test
    public void WhenSaveCarShouldCallPersistMethod() throws CarsNotFoundException, EnterpriseNotFoundException {
        DriverDTO driverDTO = DriverDTO.builder()
                .enterprise(1)
                .firstName("Smith")
                .car("2")
                .lastName("Doe")
                .birthDay("12/08/2002")
                .employedDate("06/12/2002")
                .build();

        when(enterpriseRepo.findById(1)).thenReturn(Optional.of(new Enterprise()));
        when(carRepo.findById("2")).thenReturn(Optional.of(new Car()));

        DriverDTO driverDTOResult = driverService.addOrUpdateDriver(driverDTO);

        verify(driverRepo, times(1)).save(any(Driver.class));
        assertEquals(driverDTOResult.getFirstName(),"Smith");

    }


    @Test
    public void WhenUpdateCarShouldCallPersistMethod() throws CarsNotFoundException, EnterpriseNotFoundException {
        DriverDTO driverDTO = DriverDTO.builder()
                .enterprise(1)
                .firstName("Smith")
                .car("2")
                .lastName("Doe")
                .birthDay("12/08/2002")
                .employedDate("06/12/2002")
                .build();

        when(enterpriseRepo.findById(1)).thenReturn(Optional.of(new Enterprise()));
        when(carRepo.findById("2")).thenReturn(Optional.of(new Car()));

        driverService.addOrUpdateDriver(driverDTO);

        driverDTO.setFirstName("Doe");

        DriverDTO driverDTOResult = driverService.addOrUpdateDriver(driverDTO);

        verify(driverRepo, times(2)).save(any(Driver.class));
        assertEquals(driverDTOResult.getFirstName(),"Doe");

    }

    @Test
    public void WhenDeleteCarShouldCallDeleteMethod(){
        driverService.deleteDriver(1);
        verify(driverRepo,times(1)).deleteById(1);

    }

}
