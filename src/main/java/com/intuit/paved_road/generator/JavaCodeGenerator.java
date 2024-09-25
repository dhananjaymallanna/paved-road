package com.intuit.paved_road.generator;

import com.intuit.paved_road.model.RepoSpawnModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.intuit.paved_road.Utility.JAVA_MAIN_CLASS_OUTPUT_PATH;
import static com.intuit.paved_road.Utility.getFeildsMap;

@Component
public class JavaCodeGenerator extends CodeGenerator {
    private static final Logger logger = LoggerFactory.getLogger(JavaCodeGenerator.class);
    @Autowired
    public JavaCodeGenerator(Configuration freemarkerConfig) {
        super(freemarkerConfig);
    }

    public List<String> generateJavaCode(RepoSpawnModel repoSpawnModel) {
        try {
            Map<String, Object> data = getFeildsMap(repoSpawnModel);
            String outputFilePath = data.get(JAVA_MAIN_CLASS_OUTPUT_PATH).toString();
            File outputFile = new File(outputFilePath);
            outputFile.getParentFile().mkdirs();
            Template template = freemarkerConfig.getTemplate("java/main-application.ftl"); //todo move to constant
            return generateFileFromTemplate(repoSpawnModel,template,outputFilePath);
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.error(Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
        }
    }
}
