package com.intuit.paved_road.assemble;

import com.intuit.paved_road.generator.SourceFolderGenerator;
import com.intuit.paved_road.generator.SpringApplicationGen;
import com.intuit.paved_road.generator.TestFolderGenerator;
import com.intuit.paved_road.model.RepoSpawnModel;
import com.intuit.paved_road.model.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class SpringAssembler extends Assembler {

    @Autowired
    SpringApplicationGen springApplicationGen;

    @Override
    public List<String> assemble(RepoSpawnModel repoSpawnModel) {

        List<CompletableFuture<List<String>>> futures = new ArrayList<>();

        futures.add(CompletableFuture.supplyAsync(() -> SourceFolderGenerator.generate(Type.JAVA_LIBRARY, repoSpawnModel.getArtifact())));
        futures.add(CompletableFuture.supplyAsync(() -> TestFolderGenerator.generate(Type.JAVA_LIBRARY, repoSpawnModel.getArtifact())));
        futures.add(CompletableFuture.supplyAsync(() -> springApplicationGen.generateJavaCode(repoSpawnModel)));
        futures.add(CompletableFuture.supplyAsync(() -> springApplicationGen.generateServletCode(repoSpawnModel)));
        futures.add(CompletableFuture.supplyAsync(() -> springApplicationGen.generateProperties(repoSpawnModel)));
        futures.add(CompletableFuture.supplyAsync(() -> springApplicationGen.generatePomFile(repoSpawnModel)));
        futures.add(CompletableFuture.supplyAsync(() -> copyStaticFiles(repoSpawnModel.getArtifact())));

        CompletableFuture<List<String>> combinedFuture = getListCompletableFuture(futures);
        return combinedFuture.join();
    }


    @Override
    public Boolean accept(RepoSpawnModel repoSpawnModel) {
        return repoSpawnModel.getType().equals(Type.SPRING_BOOT.toString());
    }

    public List<String> copyStaticFiles(String folder) {
        List<String> files = new ArrayList<>();
        try {
            files.add(copyFile("/templates/.gitignore",folder+"/.gitignore"));
            files.add(copyFile("/templates/java/HELP.md",folder+"/HELP.md"));
            files.add(copyFile("/templates/spring-mvn/mvnw",folder+"/mvnw"));
            files.add(copyFile("/templates/spring-mvn/mvnw.cmd",folder+"/mvnw.cmd"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(files);
        return files;
    }

}
