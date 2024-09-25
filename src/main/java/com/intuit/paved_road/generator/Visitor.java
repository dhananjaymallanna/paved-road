package com.intuit.paved_road.generator;

import java.util.List;

public interface Visitor {
    //void visit(RepoSpawnModel repoSpawnModel);
    List<String> visit(GradleGenerator springGradleAssembler);
    List<String> visit(MavenGenerator springMavenAssembler);
}
