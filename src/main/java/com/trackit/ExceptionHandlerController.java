package com.trackit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.trackit.exception.CarsNotFoundException;
import com.trackit.exception.ExceptionMessage;

@RestControllerAdvice
public class ExceptionHandlerController {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	@ExceptionHandler(CarsNotFoundException.class)
	public ResponseEntity<ExceptionMessage> CarsNotFoundExceptionHandler(HttpServletRequest request,
			CarsNotFoundException exception) {
		ExceptionMessage message = ExceptionMessage.builder().date(LocalDateTime.now().format(formatter))
				.path(request.getRequestURI().toString()).className(exception.getClass().getName())
				.message("No cars found for this enterprise!").build();
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

}
