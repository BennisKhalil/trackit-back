package com.trackit.driver;

import com.trackit.driver.DriverDTO;
import com.trackit.driver.DriverMessage;
import com.trackit.driver.DriverService;
import com.trackit.exception.*;
import com.trackit.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/enterprises")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @GetMapping("/{id}/drivers")
    private ResponseEntity<DriverMessage> getDriversByEnterprise(@PathVariable() Integer id) throws EnterpriseNotFoundException {
        List<DriverDTO> drivers = driverService.findAllDriversByEnterpriseId(id);
        DriverMessage driverMessage = DriverMessage.builder()
                .date(LocalDateTime.now().format(Utils.LocalDateTimeFormatter))
                .path("/enterprise/"+id+"/drivers")
                .drivers(drivers)
                .build();
        return new ResponseEntity<>(driverMessage, HttpStatus.OK);
    }

    @GetMapping("/{enterprise_id}/drivers/{driver_id}")
    private ResponseEntity<DriverMessage> getDriverById(@PathVariable("driver_id") Integer driverId,@PathVariable("enterprise_id") Integer enterpriseId) throws DriverNotFoundException, DriverNotFoundForEnterpriseException, EnterpriseNotFoundException {
        DriverDTO driver = driverService.getDriverByDriverIdAndEntrepriseId(enterpriseId,driverId);
        DriverMessage driverMessage = DriverMessage.builder()
                .date(LocalDateTime.now().format(Utils.LocalDateTimeFormatter))
                .path("/enterprise/"+enterpriseId+"/drive/"+driverId)
                .drivers(Collections.singletonList(driver))
                .build();
        return new ResponseEntity<>(driverMessage, HttpStatus.OK);
    }

    @PostMapping("/{enterprise_id}/drivers")
    private ResponseEntity<DriverMessage> addDriver(@Valid @RequestBody DriverDTO driverDTO,@PathVariable("enterprise_id") Integer enterpriseId) throws EnterpriseNotFoundException, DriverAlreadyExistsException, CarsNotFoundException {
        DriverDTO driver = driverService.addDriver(driverDTO, enterpriseId);
        DriverMessage driverMessage = DriverMessage.builder()
                .date(LocalDateTime.now().format(Utils.LocalDateTimeFormatter))
                .path("/enterprise"+enterpriseId+"/driver")
                .drivers(Collections.singletonList(driver))
                .build();
        return new ResponseEntity<>(driverMessage, HttpStatus.CREATED);
    }

    @PutMapping("/{enterprise_id}/drivers/{driver_id}")
    private ResponseEntity<DriverMessage> updateDriver(@Valid @RequestBody DriverDTO driverDTO,@PathVariable("enterprise_id") Integer enterpriseId, @PathVariable("driver_id") Integer driverId) throws EnterpriseNotFoundException, DriverNotFoundException, CarsNotFoundException {
        driverDTO.setId(driverId);
        DriverDTO driver = driverService.updateDriver(driverDTO,enterpriseId);
        DriverMessage driverMessage = DriverMessage.builder()
                .date(LocalDateTime.now().format(Utils.LocalDateTimeFormatter))
                .path("/enterprise"+enterpriseId+"/driver"+driverId)
                .drivers(Collections.singletonList(driver))
                .build();
        return new ResponseEntity<>(driverMessage, HttpStatus.OK);
    }


    @DeleteMapping("/{enterprise_id}/drivers/{driver_id}")
    private ResponseEntity<DriverMessage> deleteDriver(@PathVariable("driver_id") Integer driverId,@PathVariable("enterprise_id") Integer enterpriseId) throws DriverNotFoundException, EnterpriseNotFoundException, DriverNotFoundForEnterpriseException {
        driverService.deleteDriver(driverId,enterpriseId);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}