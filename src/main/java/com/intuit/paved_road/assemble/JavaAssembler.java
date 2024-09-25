package com.intuit.paved_road.assemble;

import com.intuit.paved_road.generator.JavaApplicationGen;
import com.intuit.paved_road.generator.SourceFolderGenerator;
import com.intuit.paved_road.generator.TestFolderGenerator;
import com.intuit.paved_road.model.RepoSpawnModel;
import com.intuit.paved_road.model.Type;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class JavaAssembler extends Assembler {

    @Autowired
    JavaApplicationGen javaApplicationGen;

    @Override
    public List<String> assemble(RepoSpawnModel repoSpawnModel) {

        List<CompletableFuture<List<String>>> futures = new ArrayList<>();

        // Create some futures that return lists of strings
        futures.add(CompletableFuture.supplyAsync(() -> SourceFolderGenerator.generate(Type.JAVA_LIBRARY, repoSpawnModel.getArtifact())));
        futures.add(CompletableFuture.supplyAsync(() -> TestFolderGenerator.generate(Type.JAVA_LIBRARY, repoSpawnModel.getArtifact())));
        futures.add(CompletableFuture.supplyAsync(() -> javaApplicationGen.generateJavaCode(repoSpawnModel)));
        futures.add(CompletableFuture.supplyAsync(() -> javaApplicationGen.generatePomFile(repoSpawnModel)));
        futures.add(CompletableFuture.supplyAsync(() -> copyStaticFiles(repoSpawnModel.getArtifact())));

        CompletableFuture<List<String>> combinedFuture = getListCompletableFuture(futures);
        return combinedFuture.join();
    }

    @Override
    public Boolean accept(RepoSpawnModel repoSpawnModel) {
       return repoSpawnModel.getType().equals(Type.JAVA_LIBRARY.toString());
    }

    public List<String> copyStaticFiles(String folder) {
        List<String> files = new ArrayList<>();
        try {
            files.add(copyFile("/templates/.gitignore",folder+"/.gitignore"));
            files.add(copyFile("/templates/java/HELP.md",folder+"/HELP.md"));
            System.out.println(files);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return files;
    }

}
