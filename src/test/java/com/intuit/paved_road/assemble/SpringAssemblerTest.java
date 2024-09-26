package com.intuit.paved_road.assemble;

import com.intuit.paved_road.TestUtil;
import com.intuit.paved_road.generator.SpringJavaCodeGenerator;
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

@SpringBootTest(classes = { SpringJavaCodeGenerator.class, SpringAssembler.class })
public class SpringAssemblerTest {

	@MockBean
	private SpringJavaCodeGenerator springJavaCodeGenerator;

	@Autowired
	private SpringAssembler springAssembler;

	@Test
	public void assemble() {
		RepoSpawnModel repoSpawnModel = TestUtil.getRepoModel();
		int expected = 4;
		List<String> actual = springAssembler.assemble(repoSpawnModel);
		List<String> temp = new ArrayList<>();
		temp.add("something");
		Mockito.when(springJavaCodeGenerator.generateJavaCode(any(RepoSpawnModel.class))).thenReturn(temp);

		assertEquals(expected, actual.size());
	}
}
