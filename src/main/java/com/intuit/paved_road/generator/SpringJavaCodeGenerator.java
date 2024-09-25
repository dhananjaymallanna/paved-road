package com.intuit.paved_road.generator;

import com.intuit.paved_road.model.RepoSpawnModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.intuit.paved_road.Utility.*;

@Component
public class SpringJavaCodeGenerator extends CodeGenerator {

    @Autowired
    public SpringJavaCodeGenerator(Configuration freemarkerConfig) {
        super(freemarkerConfig);
    }

    public List<String> generateJavaCode(RepoSpawnModel repoSpawnModel) {
        try {
            Map<String, Object> data = getFeildsMap(repoSpawnModel);
            String outputFilePath = data.get(JAVA_MAIN_CLASS_OUTPUT_PATH).toString();
            File outputFile = new File(outputFilePath);
            outputFile.getParentFile().mkdirs();
            Template template = freemarkerConfig.getTemplate("spring-mvn/demo-application.ftl");
            return generateFileFromTemplate(repoSpawnModel,template,outputFilePath);
        } catch (IOException e) {
            //todo CHANGE APPL
            throw new RuntimeException(e);
        }
    }

    public List<String> generateServletCode(RepoSpawnModel repoSpawnModel) {
        try {
            Map<String, Object> data = getFeildsMap(repoSpawnModel);
            String outputFilePath = data.get(JAVA_MAIN_CLASS_OUTPUT_PATH).toString();
            File outputFile = new File(outputFilePath);
            String s = outputFile.getParentFile().toPath() + "/ServletInitializer.java";
            Template template = freemarkerConfig.getTemplate("spring-mvn/servlet-template.ftl");
            return generateFileFromTemplate(repoSpawnModel,template,s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> generateProperties(RepoSpawnModel repoSpawnModel) {
        try {
            Map<String, Object> data = getFeildsMap(repoSpawnModel);
            String outputFilePath = data.get(APPLICATION_PROPERTIES).toString();
            File outputFile = new File(outputFilePath);
            Template template = freemarkerConfig.getTemplate("spring-mvn/application-properties.ftl");
            return generateFileFromTemplate(repoSpawnModel,template,outputFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
