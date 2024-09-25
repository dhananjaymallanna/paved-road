package com.intuit.paved_road.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class RepoSpawnModel {
    String buildTool;
    String type;
    String group;
    String artifact;
    String name;
    String version = "0.00.01";
    String description;
    String packaging;
    String javaVersion;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RepoSpawnModel{");
        sb.append("buildTool=").append(buildTool);
        sb.append(", type=").append(type);
        sb.append(", group='").append(group).append('\'');
        sb.append(", artifact='").append(artifact).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", version='").append(version).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", packageType=").append(packaging);
        sb.append(", javaVersion=").append(javaVersion);
        sb.append('}');
        return sb.toString();
    }
}
