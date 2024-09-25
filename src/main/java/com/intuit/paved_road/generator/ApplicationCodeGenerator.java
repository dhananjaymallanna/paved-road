package com.intuit.paved_road.generator;

import com.intuit.paved_road.model.RepoSpawnModel;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

import static com.intuit.paved_road.Utility.*;

@Component
public class ApplicationCodeGenerator {


    @Autowired
    public Configuration freemarkerConfig;

    public List<String> generateFileFromTemplate(RepoSpawnModel repoSpawnModel, Template template, String destination)  {
        Map<String, Object> data = getFeildsMap(repoSpawnModel);
        Writer writer = null;
        try {
            writer = new FileWriter(destination);
            template.process(data, writer);
            writer.flush();
            writer.close();
            System.out.println("Generated " + destination);
            return List.of(destination);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

    }

    public List<String> generateJavaCode(RepoSpawnModel repoSpawnModel) {
        try {
            Map<String, Object> data = getFeildsMap(repoSpawnModel);
            String outputFilePath = data.get(JAVA_MAIN_CLASS_OUTPUT_PATH).toString();
            File outputFile = new File(outputFilePath);
            outputFile.getParentFile().mkdirs();
            Template template = freemarkerConfig.getTemplate("java/main-application.ftl");
            return generateFileFromTemplate(repoSpawnModel,template,outputFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
