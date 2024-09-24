package com.intuit.paved_road.dependency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor
public class DependencyModel {
    private String groupId;
    private String artifactId;
    private String version;
}
