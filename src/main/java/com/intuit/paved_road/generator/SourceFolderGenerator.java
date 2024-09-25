package com.intuit.paved_road.generator;

import com.intuit.paved_road.model.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class SourceFolderGenerator {
    private static final Logger logger = LoggerFactory.getLogger(SourceFolderGenerator.class);

    public static List<String> generate(Type type,String folder) {
        List<String> sourceFolders = new ArrayList<>();
        sourceFolders.add(folder+"/src/main/java/");
        sourceFolders.add(folder+"/src/main/resources");
        if (type.equals(Type.SPRING_BOOT))
            sourceFolders.add("src/main/webapp");

        for (String sourceFolder : sourceFolders) {
            File tmp = new File( sourceFolder);
            boolean success = tmp.mkdirs();
            if (success) {
                logger.info("generated {}", tmp.getPath());
            }else {
                logger.error("FAILED to generate{}", tmp.getPath());
            }
        }
        return sourceFolders;
    }
}
