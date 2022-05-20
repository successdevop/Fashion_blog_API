package com.productblog.exception;

public class PostAlreadyExist extends RuntimeException {
    public PostAlreadyExist(String message) {
        super(message);
    }
}
