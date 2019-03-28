package com.trackit.enterprise;

import com.trackit.exception.EnterpriseAlreadyExistsException;
import com.trackit.exception.EnterpriseNotFoundException;
import com.trackit.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/enterprises")
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

    @GetMapping
    public ResponseEntity<EnterpriseMessage> listAllEnterprises() {
        List<EnterpriseDTO> enterprises = enterpriseService.findAllEnterprise();
        EnterpriseMessage enterpriseMessage = EnterpriseMessage.builder()
                .date(LocalDateTime.now().format(Utils.LocalDateTimeFormatter))
                .path("/enterprises")
                .enterprises(enterprises)
                .build();
        return new ResponseEntity<>(enterpriseMessage, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EnterpriseMessage> fetchEnterpriseById(@PathVariable Integer id) throws EnterpriseNotFoundException {
        EnterpriseDTO enterprise = enterpriseService.findEnterpriseById(id);
        EnterpriseMessage enterpriseMessage = EnterpriseMessage.builder()
                .date(LocalDateTime.now().format(Utils.LocalDateTimeFormatter))
                .path("/enterprises")
                .enterprises(Collections.singletonList(enterprise))
                .build();
        return new ResponseEntity<>(enterpriseMessage, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<EnterpriseMessage> addEnterprise(@Valid @RequestBody EnterpriseDTO enterprise) throws EnterpriseAlreadyExistsException {
        EnterpriseDTO enterpriseDTOReponse = enterpriseService.addEnterprise(enterprise);
        EnterpriseMessage enterpriseMessage = EnterpriseMessage.builder()
                .date(LocalDateTime.now().format(Utils.LocalDateTimeFormatter))
                .path("/enterprises")
                .enterprises(Collections.singletonList(enterpriseDTOReponse))
                .build();
        return new ResponseEntity<>(enterpriseMessage, HttpStatus.CREATED);
    }

    @PutMapping
    private ResponseEntity<EnterpriseMessage> updateEnterprise(@Valid @RequestBody EnterpriseDTO enterprise) throws EnterpriseAlreadyExistsException, EnterpriseNotFoundException {

        EnterpriseDTO enterpriseDTOReponse = enterpriseService.updateEnterprise(enterprise);
        EnterpriseMessage enterpriseMessage = EnterpriseMessage.builder()
                .date(LocalDateTime.now().format(Utils.LocalDateTimeFormatter))
                .path("/enterprises")
                .enterprises(Collections.singletonList(enterpriseDTOReponse))
                .build();
        return new ResponseEntity<>(enterpriseMessage, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<EnterpriseMessage> deleteEnterprise(@PathVariable Integer id) throws EnterpriseAlreadyExistsException, EnterpriseNotFoundException {
        enterpriseService.deleteEnterprise(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}