package com.trackit.enterprise;

import com.trackit.car.CarRepo;
import com.trackit.driver.DriverRepo;
import com.trackit.exception.EnterpriseAlreadyExistsException;
import com.trackit.exception.EnterpriseNotFoundException;
import com.trackit.utils.EnterpriseMappers;
import com.trackit.utils.Utils;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EnterpriseServiceTest {

    @Mock
    EnterpriseRepo enterpriseRepo;

    @Mock
    CarRepo carRepo;

    @Mock
    DriverRepo driverRepo;

    @InjectMocks
    EnterpriseServiceImpl enterpriseService;


    @Test
    public void WhenFetchingEnterprisesShouldReturnTheEnterprises() {
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
    public void WhenFetchingEnterpriseByInvalidIdShouldThrowEnterpriseNotFoundException(){
        when(enterpriseRepo.existsById(1)).thenReturn(false);
        EnterpriseNotFoundException thrown = Assertions.assertThrows(EnterpriseNotFoundException.class,()->{enterpriseService.findEnterpriseById(1);});
        assertEquals(thrown.getMessage(), "No Enterprise Found with the Id "+1);
    }

    @Test
    public void WhenFetchingEnterpriseByIdShouldReturnEnterprise() throws EnterpriseNotFoundException {
        when(enterpriseRepo.existsById(1)).thenReturn(true);
        when(enterpriseRepo.getOne(1)).thenReturn(Enterprise.builder().id(1).build());
        EnterpriseDTO enterpriseDTO= enterpriseService.findEnterpriseById(1);
        verify(enterpriseRepo,times(1)).getOne(1);
        assertEquals(enterpriseDTO.getId(), Integer.valueOf(1));
    }


    @Test
    public void WhenSavingEnterpriseShouldCallPersistMethod() throws EnterpriseAlreadyExistsException {
        EnterpriseDTO enterpriseDTO = new EnterpriseDTO();
        enterpriseDTO.setId(1);
        when(enterpriseRepo.save(any(Enterprise.class))).thenReturn(Enterprise.builder().id(1).build());
        enterpriseService.addEnterprise(enterpriseDTO);
        verify(enterpriseRepo, times(1)).save(any(Enterprise.class));
        verifyNoMoreInteractions(enterpriseRepo);

    }


    @Test
    public void WhenUpdateEnterpriseThatDoesntExistShouldThrowEnterpriseDoesntExist() {
        EnterpriseDTO enterpriseDTO = new EnterpriseDTO();
        enterpriseDTO.setId(1);
        enterpriseDTO.setName("trackit");

        when(enterpriseRepo.existsById(1)).thenReturn(false);

        EnterpriseNotFoundException thrown = Assertions.assertThrows(EnterpriseNotFoundException.class, () -> {
            enterpriseService.updateEnterprise(enterpriseDTO);
        });
        assertEquals(thrown.getMessage(), "No Enterprise Found with the Id 1");
    }

    @Test
    public void WhenUpdateEnterpriseShouldCallPersistMethod() throws EnterpriseNotFoundException, EnterpriseAlreadyExistsException {
        EnterpriseDTO enterpriseDTO = new EnterpriseDTO();
        enterpriseDTO.setId(1);
        enterpriseDTO.setName("trackit");

        when(enterpriseRepo.save(any(Enterprise.class))).thenReturn(EnterpriseMappers.maptoEnterprise(enterpriseDTO, carRepo, driverRepo));
        when(enterpriseRepo.existsById(1)).thenReturn(true);

        enterpriseService.addEnterprise(enterpriseDTO);

        enterpriseDTO.setName("trackme");
        when(enterpriseRepo.save(any(Enterprise.class))).thenReturn(EnterpriseMappers.maptoEnterprise(enterpriseDTO, carRepo, driverRepo));

        EnterpriseDTO enterpriseDTOResult = enterpriseService.updateEnterprise(enterpriseDTO);

        verify(enterpriseRepo, times(2)).save(any(Enterprise.class));
        assertEquals(enterpriseDTOResult.getName(), "trackme");
    }

    @Test
    public void WhenDeletingEnterpriseWithInvalidIdShouldThrowEnterpriseNotFoundException(){
        when(enterpriseRepo.existsById(1)).thenReturn(false);
        EnterpriseNotFoundException thrown = Assertions.assertThrows(EnterpriseNotFoundException.class,()->{enterpriseService.deleteEnterprise(1);});
        assertEquals(thrown.getMessage(), "No Enterprise Found with the Id "+1);
    }
    @Test
    public void WhenDeleteEnterpriseShouldCallDeleteMethod() throws EnterpriseNotFoundException {
        when(enterpriseRepo.existsById(1)).thenReturn(true);
        enterpriseService.deleteEnterprise(1);
        verify(enterpriseRepo, times(1)).deleteById(1);
    }


}
