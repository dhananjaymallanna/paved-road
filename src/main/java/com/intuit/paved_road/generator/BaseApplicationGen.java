package com.intuit.paved_road.generator;
import com.intuit.paved_road.model.RepoSpawnModel;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.intuit.paved_road.Utility.getFeildsMap;

@Component
public class BaseApplicationGen {

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


}
