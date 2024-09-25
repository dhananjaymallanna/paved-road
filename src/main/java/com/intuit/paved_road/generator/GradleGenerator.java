package com.intuit.paved_road.generator;

import com.intuit.paved_road.exception.GradleGenerationException;
import com.intuit.paved_road.model.RepoSpawnModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.intuit.paved_road.Utility.*;

@Component
public class GradleGenerator extends CodeGenerator {

    private static final Logger logger = LoggerFactory.getLogger(GradleGenerator.class);
    @Autowired
    public GradleGenerator(Configuration freemarkerConfig) {
        super(freemarkerConfig);
    }

    public List<String> generateBuildFile(RepoSpawnModel repoSpawnModel) {
        try {
            Map<String, Object> data = getFeildsMap(repoSpawnModel);
            String outputFilePath = data.get(BUILD_GRADLE).toString();
            Template template = freemarkerConfig.getTemplate("gradle/build-gradle.ftl");
            return generateFileFromTemplate(repoSpawnModel,template,outputFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> generateSettingGradle(RepoSpawnModel repoSpawnModel) throws GradleGenerationException {
        try {
            Map<String, Object> data = getFeildsMap(repoSpawnModel);
            String outputFilePath = data.get(SETTING_GRADLE).toString();
            File templateFile = new File(outputFilePath);
            templateFile.getParentFile().mkdirs();
            Template template = freemarkerConfig.getTemplate("gradle/settings-gradle.ftl");
            return generateFileFromTemplate(repoSpawnModel,template,outputFilePath);
        } catch (IOException e) {
            logger.error("Error generating setting.gradle ");
            throw new GradleGenerationException(e);
        }
    }


    public List<String> copyStaticFiles(String folder) throws GradleGenerationException {
        List<String> files = new ArrayList<>();
        try {
            files.add(copyFile("/templates/.gitignore",folder+"/.gitignore"));
            files.add(copyFile("/templates/java/HELP.md",folder+"/HELP.md"));
            files.add(copyFile("templates/gradle/wrapper/gradle-wrapper.jar",folder+"/gradle/wrapper/gradle-wrapper.jar"));
            files.add(copyFile("templates/gradle/wrapper/gradle-wrapper.properties",folder+"/gradle/wrapper/gradle-wrapper.properties"));
            files.add(copyFile("templates/gradle/gradlew",folder+"/gradlew"));
            files.add(copyFile("templates/gradle/gradlew.bat",folder+"/gradlew.bat"));
        } catch (IOException e) {
            logger.error("Error copying static files to Gradle project");
            throw new GradleGenerationException(e);
        }
        return files;
    }

}
