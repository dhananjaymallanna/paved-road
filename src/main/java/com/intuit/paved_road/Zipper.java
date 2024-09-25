package com.intuit.paved_road;

import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class Zipper {

    public byte[] zip(List<String> files) throws IOException {
        File zipFile = File.createTempFile("temp", ".zip");
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile))) {

            for (String file : files) {
                addFileToZip(zipOut, file);
            }
        }
        return Files.readAllBytes(zipFile.toPath());
    }

    private void addFileToZip(ZipOutputStream zipOut, String fileName) throws IOException {
        File file = new File(fileName);
        if (file.isDirectory()) {
            zipOut.putNextEntry(new ZipEntry(fileName + "/"));
            zipOut.closeEntry();
        }else{
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);
            InputStream inputStream = new FileInputStream(fileName);
            zipOut.write(inputStream.readAllBytes());
            zipOut.closeEntry();
        }
    }


}
