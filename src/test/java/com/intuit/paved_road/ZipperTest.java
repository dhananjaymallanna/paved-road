package com.intuit.paved_road;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.*;


class ZipperTest {

    @Test
    void zipEmptyList() throws IOException {
        Zipper zipper = new Zipper();
        byte[] zippedBytes = zipper.zip(new ArrayList<>());
        assertEquals(22, zippedBytes.length);
    }

    @Test
    void zipSingleFile() throws IOException {
        String filePath = new File("src/test/resources/templates/cicd/git-lab-gradle.ftl").getPath(); // Replace with your file path
        Zipper zipper = new Zipper();
        byte[] zippedBytes = zipper.zip(List.of(filePath));
        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zippedBytes))) {
            ZipEntry entry = zis.getNextEntry();
            assertNotNull(entry);
            assertEquals(filePath, entry.getName());
        }
    }

    @Test
    void zipMultipleFiles() throws IOException {
        String filePath1 = "src/test/resources/templates/cicd/git-lab-gradle.ftl";
        String filePath2 = "src/test/resources/templates/cicd/git-lab-maven.ftl";
        Zipper zipper = new Zipper();
        byte[] zippedBytes = zipper.zip(List.of(filePath1, filePath2));

        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zippedBytes))) {
            ZipEntry entry = zis.getNextEntry();
            assertNotNull(entry);
            assertEquals(filePath1, entry.getName());
            entry = zis.getNextEntry();
            assertNotNull(entry);
            assertEquals(filePath2, entry.getName());
        }
    }

    @Test
    void zipDirectory() throws IOException {
        String directoryPath = "src/test/resources/templates/cicd/";
        Zipper zipper = new Zipper();
        byte[] zippedBytes = zipper.zip(List.of(directoryPath));
        // Assert that the zipped file contains the directory structure
        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zippedBytes))) {
            ZipEntry entry = zis.getNextEntry();
            assertNotNull(entry);
            assertEquals(directoryPath + "/", entry.getName());
        }
    }
}
