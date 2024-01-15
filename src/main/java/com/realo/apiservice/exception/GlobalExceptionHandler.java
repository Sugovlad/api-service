package com.realo.apiservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Mono<ResponseEntity<String>> handleNotSavedException(MethodArgumentNotValidException ex) {
        log.error("Wrong data was occurred during validation", ex);

        return Mono.just(ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Wrong data was occurred during validation" + ex.getMessage()));
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public Mono<ResponseEntity<String>> handleNotSavedException(Throwable ex) {
        log.error("", ex);

        return Mono.just(ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unknown exception. Please try again later" + ex.getMessage()));
    }
}