package com.intuit.paved_road;

import com.intuit.paved_road.model.RepoSpawnModel;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.io.IOException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseControllerTest {

        @LocalServerPort
        private int port;

        @MockBean
        CodeGenerationService codeGenerationService;

        @MockBean
        private BaseController controller;

        @Test
        void contextLoads() throws Exception {
            assertThat(controller).isNotNull();
        }

        @Test
        void downloadShouldReturnOK() throws Exception {
                TestRestTemplate restTemplate = new TestRestTemplate();
                HttpEntity<RepoSpawnModel> requestEntity = new HttpEntity<>(TestUtil.getRepoModel());
                when(codeGenerationService.generateProject(any(RepoSpawnModel.class))).thenReturn("Mocked Data".getBytes());
                ResponseEntity<byte[]> response = restTemplate.exchange(
                        "http://localhost:" + port + "/download",
                        HttpMethod.POST,
                        requestEntity,
                        byte[].class
                );
                assert(response.getStatusCode()== HttpStatus.OK);
        }

        @Test
        void downloadTest() throws TemplateException, IOException {
                when(codeGenerationService.generateProject(any(RepoSpawnModel.class))).thenReturn("Mocked Data".getBytes());
                controller = new BaseController(codeGenerationService);
                ResponseEntity<byte[]> response = controller.download(TestUtil.getRepoModel());
                // Assert
                assert (response.getHeaders()).containsKey(HttpHeaders.CONTENT_DISPOSITION);
                assert (response.getHeaders().getContentDisposition().isFormData());
                assert (Objects.equals(response.getHeaders().getContentType(), MediaType.APPLICATION_OCTET_STREAM));
        }


}

