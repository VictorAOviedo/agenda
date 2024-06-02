package com.vao.agenda.exception;

public class OutOfTimeRangeException extends RuntimeException{
    public OutOfTimeRangeException(String message) {
        super(message);
    }
}
