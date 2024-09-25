package com.intuit.paved_road.generator;

import com.intuit.paved_road.model.RepoSpawnModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.intuit.paved_road.Utility.getFeildsMap;

@Component
public class CodeGenerator {
    private static final Logger logger = LoggerFactory.getLogger(CodeGenerator.class);
    public Configuration freemarkerConfig;

    @Autowired
    public CodeGenerator(Configuration freemarkerConfig) {
        this.freemarkerConfig = freemarkerConfig;
    }

    public List<String> generateFileFromTemplate(RepoSpawnModel repoSpawnModel, Template template, String destination) {
        Map<String, Object> data = getFeildsMap(repoSpawnModel);
        Writer writer;
        try {
            writer = new FileWriter(destination);
            template.process(data, writer);
            writer.flush();
            writer.close();
            return List.of(destination);
        } catch (IOException | TemplateException e) {
            logger.error("Error generating the file {}", destination);
            return Collections.emptyList();
        }
    }
}
