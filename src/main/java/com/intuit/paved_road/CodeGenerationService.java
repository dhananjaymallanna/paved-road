package com.intuit.paved_road;

import com.intuit.paved_road.assemble.Assembler;
import com.intuit.paved_road.assemble.JavaAssembler;
import com.intuit.paved_road.assemble.SpringAssembler;
import com.intuit.paved_road.exception.InvalidStreamException;
import com.intuit.paved_road.model.RepoSpawnModel;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class CodeGenerationService  {

    @Autowired
    SpringAssembler springAssembler;
    @Autowired
    JavaAssembler javaAssembler;


    Zipper zipper = new Zipper();

    public byte[] generateProject(RepoSpawnModel repoSpawnModel) throws IOException, TemplateException {
        List<Assembler> assemblers = Arrays.asList(javaAssembler,springAssembler);
        for (Assembler assembler : assemblers) {
            if (assembler.accept(repoSpawnModel)) {
                List<String> repo =  assembler.assemble(repoSpawnModel);
                return zipper.zip(repo);
            }
        }
        throw new InvalidStreamException();
    }

}
