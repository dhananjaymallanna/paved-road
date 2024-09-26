package com.intuit.paved_road.pipeline;

import com.intuit.paved_road.TestUtil;
import com.intuit.paved_road.generator.CodeGenerator;
import com.intuit.paved_road.model.RepoSpawnModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.intuit.paved_road.exception.PipelineGenerationException;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

@SpringBootTest(classes = { JenkinsCI.class })
public class JenkinsCITest {

	@MockBean
	Template template;
	@MockBean
	Configuration freemarkerConfig;

	@Autowired
	private JenkinsCI jenkinsCI;

	//@MockBean
	//CodeGenerator codeGenerator;

	@Test
	public void accept() {
		RepoSpawnModel repoSpawnModel = TestUtil.getRepoModel();
		Boolean expected = false;
		Boolean actual = jenkinsCI.accept(repoSpawnModel);
		assertEquals(expected, actual);
	}

	@Test
	public void generatePipelineConfigTest() throws PipelineGenerationException, IOException {

		when(freemarkerConfig.getTemplate(anyString())).thenReturn(template);

		RepoSpawnModel repoSpawnModel = TestUtil.getRepoModel();
		List<String> temp = new ArrayList<>();
		temp.add("test");
		List<String> actual = jenkinsCI.generatePipelineConfig(repoSpawnModel);
		int expected =1;
		assertEquals(expected, actual.size());
	}
}
