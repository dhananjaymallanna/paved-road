package com.intuit.paved_road;

import com.intuit.paved_road.assemble.*;
import com.intuit.paved_road.exception.InvalidStreamException;
import com.intuit.paved_road.model.RepoSpawnModel;
import freemarker.template.TemplateException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CodeGenerationService  {

    @Autowired
    SpringAssembler springAssembler;

    @Autowired
    JavaAssembler javaAssembler;

    @Autowired
    MavenAssembler mavenAssembler;

    @Autowired
    GradleAssembler gradleAssembler;


    Zipper zipper = new Zipper();

    public byte[] generateProject(RepoSpawnModel repoSpawnModel) throws IOException, TemplateException {
        List<Assembler> assemblers = Arrays.asList(javaAssembler,springAssembler, mavenAssembler, gradleAssembler);
        List<String> repo = new ArrayList<>();
        for (Assembler assembler : assemblers) {
            if (assembler.accept(repoSpawnModel)) {
                 repo.addAll(assembler.assemble(repoSpawnModel));
            }
        }
        byte[] zipOut = zipper.zip(repo);
        FileUtils.deleteDirectory(new File(repoSpawnModel.getArtifact()));
        return zipOut;
    }

}
