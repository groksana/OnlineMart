package com.gromoks.onlinemart.exception;

public class FileLoadException extends RuntimeException{
    public FileLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}