package com.trackit.driver;

import com.trackit.driver.DriverDTO;
import com.trackit.driver.DriverMessage;
import com.trackit.driver.DriverService;
import com.trackit.exception.CarsNotFoundException;
import com.trackit.exception.DriverAlreadyExistsException;
import com.trackit.exception.DriverNotFoundException;
import com.trackit.exception.EnterpriseNotFoundException;
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
@RequestMapping("/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("enterprise/{id}")
    private ResponseEntity<DriverMessage> getDriversByEnterprise(@PathVariable Integer id) throws EnterpriseNotFoundException {
        List<DriverDTO> drivers = driverService.findAllDriversByEnterpriseId(id);
        DriverMessage driverMessage = DriverMessage.builder()
                .date(LocalDateTime.now().format(Utils.LocalDateTimeFormatter))
                .path("/drivers/enterprises/"+id)
                .drivers(drivers)
                .build();
        return new ResponseEntity<>(driverMessage, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}")
    private ResponseEntity<DriverMessage> getDriverById(@PathVariable Integer id) throws DriverNotFoundException {
        DriverDTO driver = driverService.getDriver(id);
        DriverMessage driverMessage = DriverMessage.builder()
                .date(LocalDateTime.now().format(Utils.LocalDateTimeFormatter))
                .path("/drivers/"+id)
                .drivers(Collections.singletonList(driver))
                .build();
        return new ResponseEntity<>(driverMessage, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")

    @PostMapping
    private ResponseEntity<DriverMessage> addDriver(@Valid @RequestBody DriverDTO driverDTO) throws EnterpriseNotFoundException, DriverAlreadyExistsException, CarsNotFoundException {
        DriverDTO driver = driverService.addDriver(driverDTO);
        DriverMessage driverMessage = DriverMessage.builder()
                .date(LocalDateTime.now().format(Utils.LocalDateTimeFormatter))
                .path("/drivers")
                .drivers(Collections.singletonList(driver))
                .build();
        return new ResponseEntity<>(driverMessage, HttpStatus.CREATED);
    }
    @CrossOrigin(origins = "http://localhost:3000")

    @PutMapping
    private ResponseEntity<DriverMessage> updateDriver(@Valid @RequestBody DriverDTO driverDTO) throws EnterpriseNotFoundException, DriverNotFoundException, CarsNotFoundException {
        DriverDTO driver = driverService.updateDriver(driverDTO);
        DriverMessage driverMessage = DriverMessage.builder()
                .date(LocalDateTime.now().format(Utils.LocalDateTimeFormatter))
                .path("/drivers")
                .drivers(Collections.singletonList(driver))
                .build();
        return new ResponseEntity<>(driverMessage, HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:3000")

    @DeleteMapping("/{id}")
    private ResponseEntity<DriverMessage> deleteDriver(@PathVariable Integer id) throws DriverNotFoundException {
        driverService.deleteDriver(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}