package com.intuit.paved_road;

import com.intuit.paved_road.model.RepoSpawnModel;

public class TestUtil {
    public  static RepoSpawnModel  getRepoModel(){
        RepoSpawnModel repoSpawnModel = new RepoSpawnModel();
        repoSpawnModel.setBuildTool("gradle");
        repoSpawnModel.setType("spring-boot");
        repoSpawnModel.setGroup("com.example");
        repoSpawnModel.setArtifact("demo");
        repoSpawnModel.setName("demo");
        repoSpawnModel.setVersion("0.00.01");
        repoSpawnModel.setDescription("hellostring world");
        repoSpawnModel.setPackaging("war");
        repoSpawnModel.setJavaVersion("22");
        repoSpawnModel.setCicd("GitLab");

        return repoSpawnModel;
    }
}
