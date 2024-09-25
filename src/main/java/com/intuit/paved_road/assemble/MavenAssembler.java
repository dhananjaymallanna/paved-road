package com.intuit.paved_road.assemble;

import com.intuit.paved_road.generator.MavenGenerator;
import com.intuit.paved_road.generator.SourceFolderGenerator;
import com.intuit.paved_road.generator.TestFolderGenerator;
import com.intuit.paved_road.model.RepoSpawnModel;
import com.intuit.paved_road.model.Type;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class MavenAssembler extends Assembler {

    MavenGenerator mavenGenerator;

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
