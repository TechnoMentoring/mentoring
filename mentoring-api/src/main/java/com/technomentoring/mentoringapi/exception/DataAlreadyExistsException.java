package com.technomentoring.mentoringapi.exception;

public class DataAlreadyExistsException extends RuntimeException{
    public DataAlreadyExistsException(String message){
        super(message);
    }
}
