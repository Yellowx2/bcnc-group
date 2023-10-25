package com.victor.bcnc.application.error;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.victor.bcnc.application.error.exceptions.NotFoundException;

import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@Log4j2
public class ControllerExceptionHandler {

    @ExceptionHandler(value = { NotFoundException.class })
    public ResponseEntity<Object> notFoundExceptionHandler(Exception ex, WebRequest request) {
        log.error(ex.getMessage());

        return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { DateTimeParseException.class, MissingServletRequestParameterException.class })
    public ResponseEntity<ErrorMessage> badRequestExceptionHandler(Exception ex, WebRequest request) {
        log.error(ex.getMessage());

        return new ResponseEntity<ErrorMessage>(new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        log.error(ex.getMessage());

        return new ResponseEntity<ErrorMessage>(new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now(),
                ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
