package com.trackit.service;

import com.trackit.enterprise.Enterprise;
import com.trackit.enterprise.EnterpriseRepo;
import com.trackit.enterprise.EnterpriseService;
import com.trackit.enterprise.EnterpriseServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EnterpriseServiceTest {

    @Mock
    EnterpriseRepo enterpriseRepo;

    @InjectMocks
    EnterpriseServiceImpl enterpriseService;


    @Test
    public void WhenFetchingCarsByEnterpriseShouldReturnTheCars() {
        Enterprise enterprise1 = new Enterprise();
        enterprise1.setId(1);
        enterprise1.setName("TestEnterprise");

        Enterprise enterprise2 = new Enterprise();
        enterprise2.setId(1);
        enterprise2.setName("TestEnterprise");

        List<Enterprise> enterprises = new ArrayList<>();
        enterprises.add(enterprise1);
        enterprises.add(enterprise2);

        when(enterpriseRepo.findAll()).thenReturn(enterprises);

        List<Enterprise> enterpriseList = enterpriseService.findAllEnterprise();

        assertEquals(2, enterpriseList.size());
        assertEquals("TestEnterprise", enterprises.get(0).getName());
        verify(enterpriseRepo, times(1)).findAll();
    }


//    @Test
//    public void WhenSavingCarShouldCallPersistMethod() {
//        CarDTO carDTO = new CarDTO();
//        carDTO.setId("1");
//        carDTO.setBrand("ford");
//        Car carEntity = carService.map(carDTO);
//        carService.addOrUpdateCar(carDTO);
//        verify(carRepo, times(1)).save(any(Car.class));
//        assertEquals(carDTO.getId(), carEntity.getId());
//
//    }
//
//    @Test
//    public void WhenUpdateCarShouldCallPersistMethod() {
//        CarDTO carDTO = new CarDTO();
//        carDTO.setId("1");
//        carDTO.setBrand("ford");
//
//        carService.addOrUpdateCar(carDTO);
//
//        carDTO.setBrand("volvo");
//
//        CarDTO carDTOResult = carService.addOrUpdateCar(carDTO);
//
//        verify(carRepo, times(2)).save(any(Car.class));
//        assertEquals(carDTOResult.getBrand(),"volvo");
//
//    }
//
//    @Test
//    public void WhenDeleteCarShouldCallDeleteMethod(){
//        carService.deleteCarById("1");
//        verify(carRepo,times(1)).deleteById("1");
//    }


}
