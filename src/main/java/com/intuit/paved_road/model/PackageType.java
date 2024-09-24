package com.intuit.paved_road.model;

public enum PackageType {
    JAR("jar"),
    WAR("war");
    final String packageType;
    PackageType(String packageType) {
        this.packageType = packageType;
    }
}
