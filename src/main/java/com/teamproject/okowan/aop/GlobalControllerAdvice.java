package com.teamproject.okowan.aop;

import com.teamproject.okowan.common.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.concurrent.RejectedExecutionException;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler({IllegalArgumentException.class, RejectedExecutionException.class})
    public ResponseEntity<ApiResponseDto> handleException(Exception e) {
        return ResponseEntity.badRequest().body(
                new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value())
        );
    }
}
