package com.intuit.paved_road.model;

public enum Type {
    JAVA_LIBRARY("java"),
    SPRING_BOOT("spring-boot"),;

    private final String type;
    Type(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }

}
