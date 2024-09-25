package com.intuit.paved_road.generator;

import com.intuit.paved_road.Utility;
import com.intuit.paved_road.model.RepoSpawnModel;
import com.intuit.paved_road.model.Type;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class TestFolderGenerator {

    public static List<String> generate(RepoSpawnModel repoSpawnModel) {
        List<String> folders = new ArrayList<>();
        folders.add(repoSpawnModel.getArtifact()+"/src/test/");
        File tmp = new File(repoSpawnModel.getArtifact()+"/src/test/");
        tmp.mkdirs();
        File testFilePath = new File(Utility.getOutputTestFilePath(repoSpawnModel));
        testFilePath.getParentFile().mkdirs();
        folders.add(testFilePath.getParentFile().getPath());
        return folders;
    }
}
