package com.intuit.paved_road.assemble;

import com.intuit.paved_road.generator.SourceFolderGenerator;
import com.intuit.paved_road.generator.SpringJavaCodeGenerator;
import com.intuit.paved_road.generator.TestFolderGenerator;
import com.intuit.paved_road.model.RepoSpawnModel;
import com.intuit.paved_road.model.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class SpringAssembler extends Assembler  {

    private final SpringJavaCodeGenerator springApplicationCodeGenerator;
    @Autowired
    public SpringAssembler(SpringJavaCodeGenerator springApplicationCodeGenerator) {
        this.springApplicationCodeGenerator = springApplicationCodeGenerator;
    }

    @Override
    public List<String> assemble(RepoSpawnModel repoSpawnModel) {

        List<CompletableFuture<List<String>>> futures = new ArrayList<>();
        futures.add(CompletableFuture.supplyAsync(() -> SourceFolderGenerator.generate(Type.JAVA_LIBRARY, repoSpawnModel.getArtifact())));
        futures.add(CompletableFuture.supplyAsync(() -> TestFolderGenerator.generate(repoSpawnModel)));
        futures.add(CompletableFuture.supplyAsync(() -> springApplicationCodeGenerator.generateJavaCode(repoSpawnModel)));
        futures.add(CompletableFuture.supplyAsync(() -> springApplicationCodeGenerator.generateServletCode(repoSpawnModel)));
        futures.add(CompletableFuture.supplyAsync(() -> springApplicationCodeGenerator.generateProperties(repoSpawnModel)));
        CompletableFuture<List<String>> combinedFuture = getListCompletableFuture(futures);
        return combinedFuture.join();
    }

    @Override
    public Boolean accept(RepoSpawnModel repoSpawnModel) {
        return repoSpawnModel.getType().equals(Type.SPRING_BOOT.toString());
    }

}
