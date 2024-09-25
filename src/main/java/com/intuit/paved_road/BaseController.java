package com.intuit.paved_road;

import com.intuit.paved_road.model.RepoSpawnModel;
import freemarker.template.TemplateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.*;
import java.util.logging.Logger;

@Controller
public class BaseController {

    Logger logger = Logger.getLogger("BaseController");

    @Autowired
    CodeGenerationService codeGenerationService;


    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("repoSpawnModel", new RepoSpawnModel());
        return "index";
    }

    @RequestMapping(value="/download", method=RequestMethod.POST)
    public ResponseEntity<byte[]> download(@ModelAttribute RepoSpawnModel repoSpawnModel) {
        try {
            byte[] zipBytes = codeGenerationService.generateProject(repoSpawnModel);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", repoSpawnModel.getArtifact()+".zip");
            return new ResponseEntity<>(zipBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
