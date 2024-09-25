package com.intuit.paved_road.assemble;

import com.intuit.paved_road.generator.CodeGenerator;
import com.intuit.paved_road.model.RepoSpawnModel;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public abstract class Assembler {
    private static final Logger logger = LoggerFactory.getLogger(Assembler.class);
    public abstract List<String> assemble(RepoSpawnModel repoSpawnModel) throws IOException, TemplateException;

    public abstract Boolean accept(RepoSpawnModel repoSpawnModel);

    public CompletableFuture<List<String>> getListCompletableFuture(List<CompletableFuture<List<String>>> futures) {
        // Combine the results of all futures into a single list
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .flatMap(List::stream)
                        .collect(Collectors.toList()));
    }
}
