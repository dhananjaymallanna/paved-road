package com.intuit.paved_road;

import com.intuit.paved_road.model.RepoSpawnModel;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public abstract class Assembler {

    public abstract List<String> assemble(RepoSpawnModel repoSpawnModel) throws IOException, TemplateException;

    public abstract Boolean accept(RepoSpawnModel repoSpawnModel);

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
}
