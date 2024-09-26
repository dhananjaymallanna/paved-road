package com.intuit.paved_road.assemble;

import com.intuit.paved_road.TestUtil;
import com.intuit.paved_road.generator.MavenGenerator;
import com.intuit.paved_road.model.RepoSpawnModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = { MavenGenerator.class, MavenAssembler.class })
public class MavenAssemblerTest {

	@MockBean
	Configuration freemarkerConfiguration;

	@MockBean
	private MavenGenerator mavenGenerator;

	@Autowired
	private MavenAssembler mavenAssembler;


	@Test
	public void acceptTest() throws IOException, TemplateException {
		RepoSpawnModel repoSpawnModel = TestUtil.getRepoModel();
		Boolean expected = false;
		Boolean actual = mavenAssembler.accept(repoSpawnModel);

		assertEquals(expected, actual);
	}


	@Test
	public void assembleTest() throws TemplateException, IOException {
		List<String> temp = new ArrayList<>();
		temp.add("something");

		Mockito.when(mavenGenerator.generateFileFromTemplate(any(RepoSpawnModel.class), any(Template.class), any(String.class))).thenReturn(temp);
		Mockito.when(mavenGenerator.generateBuildFile(any(RepoSpawnModel.class))).thenReturn(temp);
		Mockito.when(mavenGenerator.copyStaticFiles(any(String.class))).thenReturn(temp);

		RepoSpawnModel repoSpawnModel = TestUtil.getRepoModel();

		List<String> actual = mavenAssembler.assemble(repoSpawnModel);

		assertEquals(2, actual.size());
	}
}
