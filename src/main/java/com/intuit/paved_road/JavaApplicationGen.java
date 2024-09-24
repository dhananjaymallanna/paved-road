package com.intuit.paved_road;

import com.intuit.paved_road.model.RepoSpawnModel;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

import static com.intuit.paved_road.Utility.*;

@Component
public class JavaApplicationGen extends BaseApplicationGen{

    @Autowired
    private Configuration freemarkerConfig;

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

    public List<String> generatePomFile(RepoSpawnModel repoSpawnModel) throws IOException, TemplateException {
        Map<String, Object> data = getFeildsMap(repoSpawnModel);
        String outputFilePath = data.get(POM_PATH).toString();
        Template template = freemarkerConfig.getTemplate("java/mvn/pom-template.ftl");
        return generateFileFromTemplate(repoSpawnModel,template,outputFilePath);
    }

}
