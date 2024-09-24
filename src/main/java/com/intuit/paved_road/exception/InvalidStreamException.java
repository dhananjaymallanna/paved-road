package com.intuit.paved_road.exception;

import org.springframework.stereotype.Component;

@Component
public class InvalidStreamException extends RuntimeException {
    public InvalidStreamException() {
        super("Invalid Stream");
    }
}
