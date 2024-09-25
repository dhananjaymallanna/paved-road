package com.intuit.paved_road.generator;

import com.intuit.paved_road.model.RepoSpawnModel;
import freemarker.template.Template;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.intuit.paved_road.Utility.*;
import static com.intuit.paved_road.Utility.copyFile;

@Component
public class GradleGenerator extends ApplicationCodeGenerator{

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

    public List<String> generateSettingGradle(RepoSpawnModel repoSpawnModel){
        try {
            Map<String, Object> data = getFeildsMap(repoSpawnModel);
            String outputFilePath = data.get(SETTING_GRADLE).toString();
            File templateFile = new File(outputFilePath);
            templateFile.getParentFile().mkdirs();
            Template template = freemarkerConfig.getTemplate("gradle/settings-gradle.ftl");
            return generateFileFromTemplate(repoSpawnModel,template,outputFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public List<String> copyStaticFiles(String folder) {
        List<String> files = new ArrayList<>();
        try {
            files.add(copyFile("/templates/.gitignore",folder+"/.gitignore"));
            files.add(copyFile("/templates/java/HELP.md",folder+"/HELP.md"));
            files.add(copyFile("templates/gradle/wrapper/gradle-wrapper.jar",folder+"/gradle/wrapper/gradle-wrapper.jar"));
            files.add(copyFile("templates/gradle/wrapper/gradle-wrapper.properties",folder+"/gradle/wrapper/gradle-wrapper.properties"));
            files.add(copyFile("templates/gradle/gradlew",folder+"/gradlew"));
            files.add(copyFile("templates/gradle/gradlew.bat",folder+"/gradlew.bat"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(files);
        return files;
    }

}
