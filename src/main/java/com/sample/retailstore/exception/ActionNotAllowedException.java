package com.sample.retailstore.exception;

/**
 * @author irfan.nagoo
 */
public class ActionNotAllowedException extends RuntimeException {

    public ActionNotAllowedException(String message) {
        super(message);
    }
}
