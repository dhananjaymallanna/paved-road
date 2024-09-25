package com.intuit.paved_road.exception;

import com.intuit.paved_road.assemble.Assembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class PipelineGenerationException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(PipelineGenerationException.class);
    public PipelineGenerationException(IOException e) {
        logger.error(e.getMessage());
    }

    public PipelineGenerationException(RuntimeException e) {
        logger.error(e.getMessage());
    }
}
