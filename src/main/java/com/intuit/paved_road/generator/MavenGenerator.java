package com.intuit.paved_road.generator;

import com.intuit.paved_road.model.RepoSpawnModel;
import freemarker.template.Template;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.intuit.paved_road.Utility.*;

@Component
public class MavenGenerator extends ApplicationCodeGenerator {

    public List<String> generateBuildFile(RepoSpawnModel repoSpawnModel) {
        try {
            Map<String, Object> data = getFeildsMap(repoSpawnModel);
            String outputFilePath = data.get(POM_PATH).toString();
            Template template = freemarkerConfig.getTemplate("spring-mvn/pom-template.ftl");
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
            files.add(copyFile("/templates/spring-mvn/mvnw",folder+"/mvnw"));
            files.add(copyFile("/templates/spring-mvn/mvnw.cmd",folder+"/mvnw.cmd"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(files);
        return files;
    }

}
