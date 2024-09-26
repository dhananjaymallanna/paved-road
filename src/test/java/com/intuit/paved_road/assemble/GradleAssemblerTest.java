package com.intuit.paved_road.assemble;

import com.intuit.paved_road.TestUtil;
import com.intuit.paved_road.generator.CodeGenerator;
import com.intuit.paved_road.generator.GradleGenerator;
import com.intuit.paved_road.model.RepoSpawnModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

@SpringBootTest(classes = { GradleGenerator.class, GradleAssembler.class })
public class GradleAssemblerTest {

	@MockBean
	CodeGenerator codeGenerator;

	@Autowired
	private GradleAssembler gradleAssembler;

	@MockBean
	Configuration freemarkerConfiguration;

	@MockBean
	private GradleGenerator gradleGenerator;

	@Test
	public void accept() {
		RepoSpawnModel repoSpawnModel = TestUtil.getRepoModel();
		Boolean expected = true;
		Boolean actual = gradleAssembler.accept(repoSpawnModel);

		assertEquals(expected, actual);
	}

	@Test
	public void assemble() throws TemplateException, IOException {
		List<String> temp = new ArrayList<>();
		temp.add("something");

		Mockito.when(codeGenerator.generateFileFromTemplate(any(RepoSpawnModel.class), any(Template.class), any(String.class))).thenReturn(temp);
		Mockito.when(gradleGenerator.generateBuildFile(any(RepoSpawnModel.class))).thenReturn(temp);
		Mockito.when(gradleGenerator.generateSettingGradle(any(RepoSpawnModel.class))).thenReturn(temp);
		Mockito.when(gradleGenerator.copyStaticFiles(any(String.class))).thenReturn(temp);

		RepoSpawnModel repoSpawnModel = TestUtil.getRepoModel();

		List<String> actual = gradleAssembler.assemble(repoSpawnModel);

		assertEquals(3, actual.size());
	}
}
