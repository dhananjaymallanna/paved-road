package com.intuit.paved_road.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class RepoSpawnModel {
    private String buildTool;
    private String type;
    private String group;
    private String artifact;
    private String name;
    private String version = "0.00.01";
    private String description;
    private String packaging;
    private String javaVersion;
    private String cicd;
}
