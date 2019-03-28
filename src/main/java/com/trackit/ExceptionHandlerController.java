package com.trackit;

import com.trackit.exception.*;
import com.trackit.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandlerController {


    @ExceptionHandler({CarsNotFoundException.class, CarAlreadyExistsException.class, DeviceNotFoundException.class, DriverAlreadyExistsException.class, DeviceNotFoundException.class, EnterpriseAlreadyExistsException.class, EnterpriseNotFoundException.class})
    public ResponseEntity<ExceptionMessage> exceptionHandler(HttpServletRequest request,
                                                                         Exception exception) {
        ExceptionMessage message = ExceptionMessage.builder().date(LocalDateTime.now().format(Utils.LocalDateTimeFormatter))
                .path(request.getRequestURI().toString()).className(exception.getClass().getName())
                .message(exception.getMessage()).build();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionMessage> exceptionHandler(HttpServletRequest request,
                                                             MethodArgumentNotValidException exception) {
        ExceptionMessage message = ExceptionMessage.builder().date(LocalDateTime.now().format(Utils.LocalDateTimeFormatter))
                .path(request.getRequestURI().toString()).className(exception.getClass().getName())
                .message("The Entity Sent is not Valid").build();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }


}
