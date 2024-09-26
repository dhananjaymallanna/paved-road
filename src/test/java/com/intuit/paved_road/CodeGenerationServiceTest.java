package com.intuit.paved_road;

import com.intuit.paved_road.generator.GradleGenerator;
import com.intuit.paved_road.generator.JavaCodeGenerator;
import com.intuit.paved_road.generator.MavenGenerator;
import com.intuit.paved_road.generator.SpringJavaCodeGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.intuit.paved_road.assemble.SpringAssembler;
import com.intuit.paved_road.assemble.JavaAssembler;
import com.intuit.paved_road.assemble.MavenAssembler;
import com.intuit.paved_road.assemble.GradleAssembler;
import com.intuit.paved_road.pipeline.JenkinsCI;
import com.intuit.paved_road.pipeline.GitLabCI;
import com.intuit.paved_road.exception.PipelineGenerationException;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.ArrayList;

import com.intuit.paved_road.model.RepoSpawnModel;



@SpringBootTest
class CodeGenerationServiceTest {

    @Autowired
    private CodeGenerationService codeGenerationService;

    @MockBean
    MavenGenerator mavenGenerator;

    @MockBean
    SpringJavaCodeGenerator springJavaCodeGenerator;

    @MockBean
    GradleGenerator gradleGenerator;

    @MockBean
    JavaCodeGenerator javaCodeGenerator;

    @MockBean
    SpringAssembler springAssembler;

    @MockBean
    JavaAssembler javaAssembler;

    @MockBean
    GradleAssembler gradleAssembler;

    @MockBean
    private JenkinsCI jenkinsCI;

    @MockBean
    private GitLabCI gitLabCI;

    @MockBean
    MavenAssembler mavenAssembler;

    RepoSpawnModel repoSpawnModel = TestUtil.getRepoModel();
    @MockBean
    private Zipper zipper;
    @BeforeEach
    void setUp() {


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void generateProject() {
    }

    @Test
    public void generateProjectTest() throws PipelineGenerationException, TemplateException, IOException {
        when(mavenAssembler.assemble(repoSpawnModel)).thenReturn(new ArrayList<>());
        when(gradleAssembler.assemble(repoSpawnModel)).thenReturn(new ArrayList<>());
        when(javaAssembler.assemble(repoSpawnModel)).thenReturn(new ArrayList<>());
        when(springAssembler.assemble(repoSpawnModel)).thenReturn(new ArrayList<>());
        when(zipper.zip(new ArrayList<>())).thenReturn("good".getBytes());

        byte[] actual = codeGenerationService.generateProject(repoSpawnModel);
        codeGenerationService.generateProject(repoSpawnModel);

        assertArrayEquals("good".getBytes(), actual);
    }
}
