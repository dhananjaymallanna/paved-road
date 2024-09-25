package com.intuit.paved_road.assemble;

import com.intuit.paved_road.model.RepoSpawnModel;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public abstract class Assembler {

    public abstract List<String> assemble(RepoSpawnModel repoSpawnModel) throws IOException, TemplateException;

    public abstract Boolean accept(RepoSpawnModel repoSpawnModel);

    public abstract List<String> copyStaticFiles(String folder);

    public String copyFile(String resourcePath, String generationPath) throws IOException {
        try {
            ClassPathResource resource = new ClassPathResource(resourcePath);
            File destinationFile = new File(generationPath);
            FileCopyUtils.copy(resource.getInputStream().readAllBytes(), destinationFile);
            System.out.println("File copied successfully! "+ destinationFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error copying file: " + e.getMessage());
        }
        return generationPath;
    }

    public CompletableFuture<List<String>> getListCompletableFuture(List<CompletableFuture<List<String>>> futures) {
        // Combine the results of all futures into a single list
        CompletableFuture<List<String>> combinedFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .flatMap(List::stream)
                        .collect(Collectors.toList()));
        return combinedFuture;
    }
}
