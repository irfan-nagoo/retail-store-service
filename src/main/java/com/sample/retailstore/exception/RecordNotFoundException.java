package com.sample.retailstore.exception;

/**
 * @author irfan.nagoo
 */
public class RecordNotFoundException extends RuntimeException{

    public RecordNotFoundException(String message){
        super(message);
    }
}
