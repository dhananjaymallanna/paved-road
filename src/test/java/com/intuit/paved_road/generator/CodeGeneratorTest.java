package com.intuit.paved_road.generator;

import com.intuit.paved_road.TestUtil;
import freemarker.template.Configuration;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import com.intuit.paved_road.model.RepoSpawnModel;
import freemarker.template.Template;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.ArrayList;



@SpringBootTest(classes = { CodeGenerator.class })
public class CodeGeneratorTest {

	@MockBean
	Template template;

	@MockBean
	Configuration freemarkerConfig;

	@Autowired
	private CodeGenerator codeGenerator;


	@Test
	public void generateFileFromTemplate() {
		RepoSpawnModel repoSpawnModel = TestUtil.getRepoModel();
		String destination = "abc";
		List<String> expected = new ArrayList<>();
		expected.add("abc");
		List<String> actual = codeGenerator.generateFileFromTemplate(repoSpawnModel, template, destination);

		assertEquals(expected, actual);
	}
}
