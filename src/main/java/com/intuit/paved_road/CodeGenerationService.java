package com.intuit.paved_road;

import com.intuit.paved_road.assemble.*;
import com.intuit.paved_road.exception.PipelineGenerationException;
import com.intuit.paved_road.model.RepoSpawnModel;
import com.intuit.paved_road.pipeline.GitLabCI;
import com.intuit.paved_road.pipeline.JenkinsCI;
import com.intuit.paved_road.pipeline.Pipeline;
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
    private SpringAssembler springAssembler;

    @Autowired
    private JavaAssembler javaAssembler;

    @Autowired
    private MavenAssembler mavenAssembler;

    @Autowired
    private GradleAssembler gradleAssembler;

    @Autowired
    private JenkinsCI jenkinsCI;

    @Autowired
    private GitLabCI gitLabCI;

    @Autowired
    private Zipper zipper;

    public byte[] generateProject(RepoSpawnModel repoSpawnModel) throws IOException, TemplateException, PipelineGenerationException {
        List<Assembler> assemblers = Arrays.asList(javaAssembler,springAssembler, mavenAssembler, gradleAssembler);
        List<Pipeline> pipelines = Arrays.asList(jenkinsCI,gitLabCI);

        List<String> repo = new ArrayList<>();
        for (Assembler assembler : assemblers) {
            if (assembler.accept(repoSpawnModel)) {
                 repo.addAll(assembler.assemble(repoSpawnModel));
            }
        }

        for (Pipeline pipeline : pipelines) {
            if (pipeline.accept(repoSpawnModel)) {
                repo.addAll(pipeline.generatePipelineConfig(repoSpawnModel));
            }
        }

        byte[] zipOut = zipper.zip(repo);
        FileUtils.deleteDirectory(new File(repoSpawnModel.getArtifact()));
        return zipOut;
    }

}
