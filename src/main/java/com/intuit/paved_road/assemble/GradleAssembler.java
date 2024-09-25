package com.intuit.paved_road.assemble;

import com.intuit.paved_road.generator.GradleGenerator;
import com.intuit.paved_road.model.RepoSpawnModel;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class GradleAssembler extends Assembler {

    GradleGenerator gradleGenerator;

    @Autowired
    public GradleAssembler(GradleGenerator gradleGenerator) {
        this.gradleGenerator = gradleGenerator;
    }

    @Override
    public List<String> assemble(RepoSpawnModel repoSpawnModel) throws IOException, TemplateException {
        List<CompletableFuture<List<String>>> futures = new ArrayList<>();
        futures.add(CompletableFuture.supplyAsync(() -> gradleGenerator.generateBuildFile(repoSpawnModel)));
        futures.add(CompletableFuture.supplyAsync(() -> gradleGenerator.generateSettingGradle(repoSpawnModel)));
        futures.add(CompletableFuture.supplyAsync(() -> gradleGenerator.copyStaticFiles(repoSpawnModel.getArtifact())));
        CompletableFuture<List<String>> combinedFuture = getListCompletableFuture(futures);
        return combinedFuture.join();
    }

    @Override
    public Boolean accept(RepoSpawnModel repoSpawnModel) {
        return repoSpawnModel.getBuildTool().equals("gradle");
    }
}
