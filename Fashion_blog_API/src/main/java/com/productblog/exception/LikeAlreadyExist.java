package com.productblog.exception;

public class LikeAlreadyExist extends RuntimeException{
    public LikeAlreadyExist(String message) {
        super(message);
    }
}
