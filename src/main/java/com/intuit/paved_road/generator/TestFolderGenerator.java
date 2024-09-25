package com.intuit.paved_road.generator;

import com.intuit.paved_road.model.Type;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestFolderGenerator {

    public static List<String> generate(Type javaLibrary, String folder) {
        List<String> folders = new ArrayList<>();
        folders.add(folder+"/src/test/");
        File tmp = new File(folder+"/src/test/");
        tmp.mkdirs();
        return folders;
    }
}
