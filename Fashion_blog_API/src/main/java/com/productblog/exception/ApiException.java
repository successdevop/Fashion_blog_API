package com.productblog.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
public class ApiException {
    private HttpStatus httpStatus;
    private String message;
    private LocalDateTime localDateTime;
}
