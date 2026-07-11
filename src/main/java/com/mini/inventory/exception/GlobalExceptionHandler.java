package com.mini.inventory.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(
            DuplicateSkuException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateSku(
            DuplicateSkuException ex, HttpServletRequest request){

        ErrorResponse response =
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(409)
                        .error("Duplicate SKU")
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build();

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);

    }
}
