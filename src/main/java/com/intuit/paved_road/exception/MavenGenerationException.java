package com.intuit.paved_road.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MavenGenerationException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(MavenGenerationException.class);
    public MavenGenerationException(IOException e) {
        logger.error(e.getMessage());
    }

    public MavenGenerationException(RuntimeException e) {
        logger.error(e.getMessage());
    }
}
