package com.trackit.driver;


import com.trackit.car.Car;
import com.trackit.car.CarRepo;
import com.trackit.enterprise.Enterprise;
import com.trackit.enterprise.EnterpriseRepo;
import com.trackit.exception.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.trackit.utils.DriverMappers.*;
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
    public void WhenFetchingAllDriversWithInvalidEnterpriseIdShouldThrowEnterpriseNotFoundException() {
        when(enterpriseRepo.existsById(1)).thenReturn(false);
        EnterpriseNotFoundException thrown = assertThrows(EnterpriseNotFoundException.class,
                () -> {
                    driverService.findAllDriversByEnterpriseId(1);
                });

        assertEquals(thrown.getMessage(),"No Enterprise Found with the Id 1");

    }


    @Test
    public void WhenFetchingDriversByEnterpriseShouldReturnTheDrivers() throws EnterpriseNotFoundException {
        Enterprise enterprise = new Enterprise();
        enterprise.setId(1);
        enterprise.setName("TestEnterprise");


        List<Driver> mockedDrivers = new ArrayList<>();

        Driver driver1 = Driver.builder().id(1)
                .firstName("driver1")
                .enterprise(enterprise)
                .birthDay(LocalDate.now())
                .employedDate(LocalDate.now())
                .car(Car.builder().id("1").build())
                .build();

        Driver driver2 = Driver.builder().id(2)
                .firstName("driver2")
                .enterprise(enterprise)
                .birthDay(LocalDate.now())
                .employedDate(LocalDate.now())
                .car(Car.builder().id("2").build())
                .build();

        mockedDrivers.add(driver1);
        mockedDrivers.add(driver2);

        enterprise.setDrivers(mockedDrivers);
        when(enterpriseRepo.existsById(1)).thenReturn(true);
       when(driverRepo.findByEnterpriseId(1)).thenReturn(mockedDrivers);

        List<DriverDTO> drivers = driverService.findAllDriversByEnterpriseId(1);

        assertEquals(2, drivers.size());
        //assertEquals(new Integer(1), drivers.get(0));
        verify(enterpriseRepo, times(1)).existsById(1);
        verify(driverRepo, times(1)).findByEnterpriseId(1);
        verifyNoMoreInteractions(driverRepo);
    }


    @Test
    public void WhenFetchingDriverForEnterpriseWithInvalidEnterpriseIdShouldThrowEnterpriseNotFoundException(){
        when(enterpriseRepo.existsById(1)).thenReturn(false);

        EnterpriseNotFoundException thrown = Assertions.assertThrows(EnterpriseNotFoundException.class,()->{driverService.getDriverByDriverIdAndEntrepriseId(1,1);});
        assertEquals(thrown.getMessage(),"No Enterprise Found with the Id 1");
    }

    @Test
    public void WhenFetchingDriverForEnterpriseWithInvalidIdShouldThrowCarNotFoundForEntrepriseException(){
        when(enterpriseRepo.existsById(1)).thenReturn(true);
        when(driverRepo.findByEnterpriseIdAndId(1,1)).thenReturn(Optional.empty());
        DriverNotFoundForEnterpriseException thrown = Assertions.assertThrows(DriverNotFoundForEnterpriseException.class,()->{driverService.getDriverByDriverIdAndEntrepriseId(1,1);});
        assertEquals(thrown.getMessage(),"no driver with the id "+1+" is found in this enterprise");
    }

    @Test
    public void WhenFetchingDriverForEnterpriseWithValidIdShouldReturnCar() throws EnterpriseNotFoundException, DriverNotFoundForEnterpriseException {

        when(enterpriseRepo.existsById(1)).thenReturn(true);
        Enterprise enterprise = Enterprise.builder().id(1).build();
        Driver driver = Driver.builder().id(1).enterprise(enterprise).birthDay(LocalDate.now()).employedDate(LocalDate.now()).build();
        when(driverRepo.findByEnterpriseIdAndId(1,1)).thenReturn(Optional.of(driver));

        DriverDTO driverDTO = driverService.getDriverByDriverIdAndEntrepriseId(1,1);
        verify(driverRepo,times(1)).findByEnterpriseIdAndId(1,1);
        assertEquals(driverDTO.getId(),Integer.valueOf(1));
    }




    @Test
    public void WhenSavingDriverWithInvalidCarIdShouldReturnCarAlreadyExistsException() {
        when(carRepo.existsById("1")).thenReturn(false);
        DriverDTO driverDTO = DriverDTO.builder().id(1).car("1").build();
        CarsNotFoundException thrown = Assertions.assertThrows(CarsNotFoundException.class, () -> {
            driverService.addDriver(driverDTO,1);
        });
        assertEquals(thrown.getMessage(), "No Car Found with the Id " + driverDTO.getCar());
    }

    @Test
    public void WhenSaveDriverWithInvalidEnterpriseIdShouldReturnCarAlreadyExistsException() {
        when(carRepo.existsById("1")).thenReturn(true);
        when(enterpriseRepo.existsById(1)).thenReturn(false);
        DriverDTO driverDTO = DriverDTO.builder().id(1).car("1").build();
        EnterpriseNotFoundException thrown = Assertions.assertThrows(EnterpriseNotFoundException.class, () -> {
            driverService.addDriver(driverDTO,1);
        });
        assertEquals(thrown.getMessage(), "No Enterprise Found with the Id " +1);
    }


    @Test
    public void WhenSavingDriverShouldCallPersistMethod() throws CarsNotFoundException, EnterpriseNotFoundException, DriverAlreadyExistsException {
        when(carRepo.existsById("1")).thenReturn(true);
        when(enterpriseRepo.existsById(1)).thenReturn(true);
        when(carRepo.getOne("1")).thenReturn(Car.builder().id("1").build());

        DriverDTO driverDTO = DriverDTO.builder()
                .id(1)
                .car("1")
                .firstName("Smith")
                .birthDay("12/08/2002")
                .employedDate("25/12/2009")
                .build();
        Driver driver = mapToDriver(driverDTO,enterpriseRepo, carRepo,1);
        driver.setEnterprise(Enterprise.builder().id(1).build());
        when(driverRepo.save(any(Driver.class))).thenReturn(driver);
        DriverDTO driverDTOResult = driverService.addDriver(driverDTO,1);

        verify(driverRepo, times(1)).save(any(Driver.class));
        assertEquals(driverDTOResult.getFirstName(), "Smith");

    }

    @Test
    public void WhenUpdatingDriverThatAlreadyExistsShouldThrowCarAlreadyExistsException() {
        when(driverRepo.existsById(1)).thenReturn(true);
        DriverAlreadyExistsException thrown = Assertions.assertThrows(DriverAlreadyExistsException.class, () -> {
            driverService.addDriver(DriverDTO.builder().id(1).build(),1);
        });
        assertEquals(thrown.getMessage(), "Driver with the Id 1 Already Exists");
    }



    @Test
    public void WhenUpdateDriverWithInvalidIdShouldReturnCarAlreadyExistsException() {
        when(driverRepo.existsById(1)).thenReturn(false);
        DriverDTO driverDTO = DriverDTO.builder().id(1).build();
        DriverNotFoundException thrown = Assertions.assertThrows(DriverNotFoundException.class, () -> {
            driverService.updateDriver(driverDTO,1);
        });
        assertEquals(thrown.getMessage(), "No Driver Found with the Id " + driverDTO.getId());
    }

    @Test
    public void WhenUpdateCarWithInvalidCarIdShouldReturnCarAlreadyExistsException() {
        when(driverRepo.existsById(1)).thenReturn(true);
        when(carRepo.existsById("1")).thenReturn(false);
        DriverDTO driverDTO = DriverDTO.builder().id(1).car("1").build();
        CarsNotFoundException thrown = Assertions.assertThrows(CarsNotFoundException.class, () -> {
            driverService.updateDriver(driverDTO,1);
        });
        assertEquals(thrown.getMessage(), "No Car Found with the Id " + driverDTO.getCar());
    }

    @Test
    public void WhenUpdateCarWithInvalidEnterpriseIdShouldReturnCarAlreadyExistsException() {
        when(driverRepo.existsById(1)).thenReturn(true);
        when(carRepo.existsById("1")).thenReturn(true);
        when(enterpriseRepo.existsById(1)).thenReturn(false);
        DriverDTO driverDTO = DriverDTO.builder().id(1).car("1").build();
        EnterpriseNotFoundException thrown = Assertions.assertThrows(EnterpriseNotFoundException.class, () -> {
            driverService.updateDriver(driverDTO,1);
        });
        assertEquals(thrown.getMessage(), "No Enterprise Found with the Id " + 1);
    }


    @Test
    public void WhenUpdateCarShouldCallPersistMethod() throws CarsNotFoundException, EnterpriseNotFoundException, DriverNotFoundException, DriverAlreadyExistsException {
        when(driverRepo.existsById(1)).thenReturn(true);
        when(carRepo.existsById("1")).thenReturn(true);
        when(enterpriseRepo.existsById(1)).thenReturn(true);
        when(carRepo.getOne("1")).thenReturn(Car.builder().id("1").build());

        DriverDTO driverDTO = DriverDTO.builder()
                .id(1)
                .car("1")
                .firstName("Smith")
                .birthDay("12/08/2002")
                .employedDate("25/12/2009")
                .build();
        Driver driver = mapToDriver(driverDTO, enterpriseRepo, carRepo,1 );
        driver.setEnterprise(Enterprise.builder().id(1).build());
        when(driverRepo.save(any(Driver.class))).thenReturn(driver);
        DriverDTO driverDTOResult = driverService.updateDriver(driverDTO,1);
        verify(driverRepo, times(1)).save(any(Driver.class));
    }

    @Test
    public void WhenDeletingDriverWithInvalidIdShouldThrowDriverNotFoundException(){
        when(enterpriseRepo.existsById(1)).thenReturn(true);
        when(driverRepo.findByEnterpriseIdAndId(1,1)).thenReturn(Optional.empty());
        DriverNotFoundForEnterpriseException thrown = Assertions.assertThrows(DriverNotFoundForEnterpriseException.class,()->{driverService.deleteDriver(1,1);});
        assertEquals(thrown.getMessage(), "No Driver Found with the Id 1");
    }

    @Test
    public void WhenDeleteCarShouldCallDeleteMethod() throws DriverNotFoundException, EnterpriseNotFoundException, DriverNotFoundForEnterpriseException
    {
        when(enterpriseRepo.existsById(1)).thenReturn(true);
        when(driverRepo.findByEnterpriseIdAndId(1,1)).thenReturn(Optional.of(Driver.builder().id(1).build()));
        driverService.deleteDriver(1,1);
        verify(driverRepo, times(1)).deleteById(1);
    }

}
