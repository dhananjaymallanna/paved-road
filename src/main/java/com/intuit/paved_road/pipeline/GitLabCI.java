package com.intuit.paved_road.pipeline;

import com.intuit.paved_road.generator.CodeGenerator;
import com.intuit.paved_road.generator.MavenGenerator;
import com.intuit.paved_road.model.BuildTool;
import com.intuit.paved_road.model.RepoSpawnModel;
import com.intuit.paved_road.model.Type;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.intuit.paved_road.Utility.*;

@Component
public class GitLabCI extends CodeGenerator implements Pipeline{

    private static final Logger logger = LoggerFactory.getLogger(GitLabCI.class);

    @Autowired
    public GitLabCI(Configuration freemarkerConfig) {
        super(freemarkerConfig);
    }

    public List<String> generatePipelineConfig(RepoSpawnModel repoSpawnModel) {
        try {
            Map<String, Object> data = getFeildsMap(repoSpawnModel);
            String outputFilePath = data.get(GITLAB_CI_CD_PATH).toString();
            Template template = getTemplate(repoSpawnModel.getBuildTool());
            return generateFileFromTemplate(repoSpawnModel,template,outputFilePath);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean accept(RepoSpawnModel repoSpawnModel) {
        return repoSpawnModel.getCicd().equals("GitLab");
    }

    private Template getTemplate(String buildTool) throws IOException {
        if (buildTool.equals(BuildTool.MAVEN.toString())) {
            return freemarkerConfig.getTemplate("cicd/git-lab-maven.ftl");
        }else{
            return freemarkerConfig.getTemplate("cicd/git-lab-gradle.ftl");
        }
    }
}
