package com.intuit.paved_road;

import com.intuit.paved_road.model.Type;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class SourceFolderGenerator {

    public static List<String> generate(Type type,String folder) {
        List<String> sourceFolders = new ArrayList<>();
        sourceFolders.add(folder+"/src/main/java/");
       // if (type.equals(Type.SPRING_BOOT))
        sourceFolders.add(folder+"/src/main/resources");
        //sourceFolders.add("src/main/webapp");

        for (String sourceFolder : sourceFolders) {
            File tmp = new File( sourceFolder);
            Boolean success = tmp.mkdirs();
            if (success) {
                System.out.println(tmp.getAbsolutePath());
            }else {
                System.out.println("FAILED===============");
            }
        }
        System.out.println(sourceFolders.toString());
        return sourceFolders;
    }
}
