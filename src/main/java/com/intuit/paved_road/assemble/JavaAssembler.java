package com.intuit.paved_road.assemble;

import com.intuit.paved_road.generator.ApplicationCodeGenerator;
import com.intuit.paved_road.generator.MavenGenerator;
import com.intuit.paved_road.generator.SourceFolderGenerator;
import com.intuit.paved_road.generator.TestFolderGenerator;
import com.intuit.paved_road.model.RepoSpawnModel;
import com.intuit.paved_road.model.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class JavaAssembler extends Assembler {

    @Autowired
    ApplicationCodeGenerator applicationCodeGenerator;

    RepoSpawnModel repoSpawnModel;
    @Autowired
    public JavaAssembler(RepoSpawnModel repoSpawnModel) {
        this.repoSpawnModel = repoSpawnModel;
    }

    @Override
    public List<String> assemble(RepoSpawnModel repoSpawnModel) {

        List<CompletableFuture<List<String>>> futures = new ArrayList<>();
        futures.add(CompletableFuture.supplyAsync(() -> SourceFolderGenerator.generate(Type.JAVA_LIBRARY, repoSpawnModel.getArtifact())));
        futures.add(CompletableFuture.supplyAsync(() -> TestFolderGenerator.generate(repoSpawnModel)));
        futures.add(CompletableFuture.supplyAsync(() -> applicationCodeGenerator.generateJavaCode(repoSpawnModel)));

        CompletableFuture<List<String>> combinedFuture = getListCompletableFuture(futures);
        return combinedFuture.join();
    }

    @Override
    public Boolean accept(RepoSpawnModel repoSpawnModel) {
       return repoSpawnModel.getType().equals(Type.JAVA_LIBRARY.toString());
    }

}
