package com.intuit.paved_road.pipeline;

import com.intuit.paved_road.exception.PipelineGenerationException;
import com.intuit.paved_road.model.RepoSpawnModel;

import java.util.List;

public interface Pipeline {
    public List<String> generatePipelineConfig(RepoSpawnModel repoSpawnModel) throws PipelineGenerationException;
    public Boolean accept(RepoSpawnModel repoSpawnModel);

}
