package com.trackit.enterprise;

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
        enterprise1.setName("TestEnterprise1");

        Enterprise enterprise2 = new Enterprise();
        enterprise2.setId(2);
        enterprise2.setName("TestEnterprise2");

        List<Enterprise> enterprises = new ArrayList<>();
        enterprises.add(enterprise1);
        enterprises.add(enterprise2);

        when(enterpriseRepo.findAll()).thenReturn(enterprises);

        List<EnterpriseDTO> enterpriseList = enterpriseService.findAllEnterprise();

        assertEquals(2, enterpriseList.size());
        assertEquals("TestEnterprise1", enterprises.get(0).getName());
        verify(enterpriseRepo, times(1)).findAll();
    }


    @Test
    public void WhenSavingInterpriseShouldCallPersistMethod() {
        EnterpriseDTO enterpriseDTO = new EnterpriseDTO();
        enterpriseDTO.setId(1);

        enterpriseService.addOrUpdateEntreprise(enterpriseDTO);
        verify(enterpriseRepo, times(1)).save(any(Enterprise.class));

    }

    @Test
    public void WhenUpdateCarShouldCallPersistMethod() {
        EnterpriseDTO enterpriseDTO = new EnterpriseDTO();
        enterpriseDTO.setId(1);
        enterpriseDTO.setName("trackit");

        enterpriseService.addOrUpdateEntreprise(enterpriseDTO);

        enterpriseDTO.setName("trackme");

        EnterpriseDTO enterpriseDTOResult = enterpriseService.addOrUpdateEntreprise(enterpriseDTO);

        verify(enterpriseRepo, times(2)).save(any(Enterprise.class));
        assertEquals(enterpriseDTOResult.getName(),"trackme");

    }

    @Test
    public void WhenDeleteCarShouldCallDeleteMethod(){
        enterpriseService.deleteEnterprise(1);
        verify(enterpriseRepo,times(1)).deleteById(1);
    }


}
