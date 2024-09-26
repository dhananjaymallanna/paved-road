package com.intuit.paved_road.assemble;

import com.intuit.paved_road.TestUtil;
import com.intuit.paved_road.generator.JavaCodeGenerator;
import com.intuit.paved_road.model.RepoSpawnModel;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = { JavaCodeGenerator.class, JavaAssembler.class })
public class JavaAssemblerTest {
	@Autowired
	private JavaAssembler javaAssembler;

	@MockBean
	private JavaCodeGenerator javaCodeGenerator;

	@Test
	public void assembleTest() {
		RepoSpawnModel repoSpawnModel = TestUtil.getRepoModel();
		List<String> temp = new ArrayList<>();
		temp.add("something");
		Mockito.when(javaCodeGenerator.generateJavaCode(any(RepoSpawnModel.class))).thenReturn(temp);
		int expected = 5;
		List<String> actual = javaAssembler.assemble(repoSpawnModel);
		assertEquals(expected, actual.size());
	}

	@Test
	public void acceptTest() {
		RepoSpawnModel repoSpawnModel = TestUtil.getRepoModel();
		assertFalse(javaAssembler.accept(repoSpawnModel));
	}
}
