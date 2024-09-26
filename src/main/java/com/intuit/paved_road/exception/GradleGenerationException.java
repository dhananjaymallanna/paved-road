package com.intuit.paved_road.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GradleGenerationException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(GradleGenerationException.class);
    public GradleGenerationException(IOException e) {
        logger.error(e.getMessage());
    }

    public GradleGenerationException(String message) {
        logger.error(message);
    }
}
