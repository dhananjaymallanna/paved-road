package com.intuit.paved_road.pipeline;

import com.intuit.paved_road.TestUtil;
import com.intuit.paved_road.exception.PipelineGenerationException;
import com.intuit.paved_road.model.RepoSpawnModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = { GitLabCI.class })
public class GitLabCITest {

    @MockBean
    Template template;

    @MockBean
    Configuration freemarkerConfig;

    @Autowired
    private GitLabCI gitLabCI;

    @Test
    public void accept() {
        RepoSpawnModel repoSpawnModel = TestUtil.getRepoModel();
        Boolean expected = true;
        Boolean actual = gitLabCI.accept(repoSpawnModel);
        assertEquals(expected, actual);
    }

    @Test
    public void generatePipelineConfigTest() throws PipelineGenerationException, IOException {

        when(freemarkerConfig.getTemplate(anyString())).thenReturn(template);

        RepoSpawnModel repoSpawnModel = TestUtil.getRepoModel();
        List<String> temp = new ArrayList<>();
        temp.add("test");
        List<String> actual = gitLabCI.generatePipelineConfig(repoSpawnModel);
        int expected =1;
        assertEquals(expected, actual.size());
    }
}
