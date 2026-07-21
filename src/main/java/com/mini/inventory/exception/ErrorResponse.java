package com.mini.inventory.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;

    private Integer status;

    private String error;

    private String message;

    private String path;

    private String traceId;
}
