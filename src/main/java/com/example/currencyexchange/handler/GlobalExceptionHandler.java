package com.example.currencyexchange.handler;

import com.example.currencyexchange.helper.ResponseUtils;
import com.example.currencyexchange.model.resp.Message;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({BindException.class, RuntimeException.class})
    public ResponseEntity<?> validateException(Exception e) {
        logger.info("Exception", e);
        if (e instanceof BindException) {
            List<String> fieldErrors = ((BindException) e).getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseUtils.data(fieldErrors, false);
        }
        return ResponseUtils.data(Collections.singletonList(e.getMessage()), false);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> exception(Exception e) {
//        return ResponseUtils.error(e.getMessage());
//    }

}
