package com.intuit.paved_road.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepoSpawnModel_new {
    PackageType projectType;
    Type type;
    String group;
    String artifact;
    String name;
    String version;
    String description;
    String packaging;
    String javaVersion;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RepoSpawnModel{");
        sb.append("projectType=").append(projectType);
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
