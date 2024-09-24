package com.intuit.paved_road.model;

public enum BuildTool {
    MAVEN("maven"),
    GRADLE("gradle");

    final String buildTool;

    BuildTool(String buildTool) {
        this.buildTool = buildTool;
    }
}
