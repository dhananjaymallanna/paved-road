package com.intuit.paved_road;

import com.intuit.paved_road.model.BuildTool;
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
public class SpringAssembler extends Assembler {

    @Autowired
    JavaApplicationGen javaApplicationGen;

    @Override
    public List<String> assemble(RepoSpawnModel repoSpawnModel) {

        List<CompletableFuture<List<String>>> futures = new ArrayList<>();

        // Create some futures that return lists of strings
        futures.add(CompletableFuture.supplyAsync(() -> SourceFolderGenerator.generate(Type.JAVA_LIBRARY, repoSpawnModel.getArtifact())));
        futures.add(CompletableFuture.supplyAsync(() -> TestFolderGenerator.generate(Type.JAVA_LIBRARY, repoSpawnModel.getArtifact())));
        futures.add(CompletableFuture.supplyAsync(() -> javaApplicationGen.generateJavaCode(repoSpawnModel)));

        futures.add(CompletableFuture.supplyAsync(() -> {
            try {
                return javaApplicationGen.generatePomFile(repoSpawnModel);
            } catch (IOException | TemplateException e) {
                System.out.println(e.getMessage());
            }
            return Collections.emptyList();
        }));
        futures.add(CompletableFuture.supplyAsync(() -> {
            try {
                return buildFiles(repoSpawnModel.getArtifact());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            return Collections.emptyList();
        }));

        // Combine the results of all futures into a single list
        CompletableFuture<List<String>> combinedFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .flatMap(List::stream)
                        .collect(Collectors.toList()));

        // Get the combined list
        return combinedFuture.join();
    }

    @Override
    public Boolean accept(RepoSpawnModel repoSpawnModel) {
        // return repoSpawnModel.getType().equals(Type.JAVA_LIBRARY);
        return true;
    }

    public List<String> buildFiles(String folder) throws IOException {
        List<String> files = new ArrayList<>();
        files.add(copyFile("/templates/.gitignore",folder+"/.gitignore"));
        files.add(copyFile("/templates/java/HELP.md",folder+"/HELP.md"));
        System.out.println(files);
        return files;
    }

}
