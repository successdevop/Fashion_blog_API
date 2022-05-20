package com.productblog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({
            UserNotFound.class,
            UserAlreadyExist.class,
            PostNotFound.class,
            PostAlreadyExist.class,
            CategoryNotFound.class,
            CategoryAlreadyExist.class,
            CommentNotFound.class,
            LikeAlreadyExist.class,
            IllegalCategory.class

    })
    public ResponseEntity<ApiException> handleExceptions(Exception ex){
        ApiException exception = new ApiException(
          HttpStatus.BAD_REQUEST,
          ex.getMessage(),
          LocalDateTime.now()
        );
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}
