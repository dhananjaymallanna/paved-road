package com.intuit.paved_road.assemble;

import com.intuit.paved_road.exception.MavenGenerationException;
import com.intuit.paved_road.generator.CodeGenerator;
import com.intuit.paved_road.generator.MavenGenerator;
import com.intuit.paved_road.model.RepoSpawnModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class MavenAssembler extends Assembler {
    private static final Logger logger = LoggerFactory.getLogger(MavenAssembler.class);
    private final MavenGenerator mavenGenerator;

    @Autowired
    public MavenAssembler(MavenGenerator mavenGenerator) {
        this.mavenGenerator = mavenGenerator;
    }

    @Override
    public List<String> assemble(RepoSpawnModel repoSpawnModel) {

        List<CompletableFuture<List<String>>> futures = new ArrayList<>();
        futures.add(CompletableFuture.supplyAsync(() -> mavenGenerator.generateBuildFile(repoSpawnModel)));
        futures.add(CompletableFuture.supplyAsync(() -> mavenGenerator.copyStaticFiles(repoSpawnModel.getArtifact())));
        CompletableFuture<List<String>> combinedFuture = getListCompletableFuture(futures);
        return combinedFuture.join();
    }

    @Override
    public Boolean accept(RepoSpawnModel repoSpawnModel) {
        return repoSpawnModel.getBuildTool().equals("maven");
    }

}
