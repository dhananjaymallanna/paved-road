package com.intuit.paved_road.assemble;

import com.intuit.paved_road.generator.JavaCodeGenerator;
import com.intuit.paved_road.generator.SourceFolderGenerator;
import com.intuit.paved_road.generator.TestFolderGenerator;
import com.intuit.paved_road.model.RepoSpawnModel;
import com.intuit.paved_road.model.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class JavaAssembler extends Assembler {
    private static final Logger logger = LoggerFactory.getLogger(JavaAssembler.class);
    private final JavaCodeGenerator javaCodeGenerator;

    @Autowired
    public JavaAssembler(JavaCodeGenerator javaCodeGenerator) {
        this.javaCodeGenerator = javaCodeGenerator;
    }

    @Override
    public List<String> assemble(RepoSpawnModel repoSpawnModel) {

        List<CompletableFuture<List<String>>> futures = new ArrayList<>();
        futures.add(CompletableFuture.supplyAsync(() -> SourceFolderGenerator.generate(Type.JAVA_LIBRARY, repoSpawnModel.getArtifact())));
        futures.add(CompletableFuture.supplyAsync(() -> TestFolderGenerator.generate(repoSpawnModel)));
        futures.add(CompletableFuture.supplyAsync(() -> javaCodeGenerator.generateJavaCode(repoSpawnModel)));

        CompletableFuture<List<String>> combinedFuture = getListCompletableFuture(futures);
        return combinedFuture.join();
    }

    @Override
    public Boolean accept(RepoSpawnModel repoSpawnModel) {
       return repoSpawnModel.getType().equals(Type.JAVA_LIBRARY.toString());
    }

}
