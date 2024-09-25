package com.intuit.paved_road;

import com.intuit.paved_road.model.RepoSpawnModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.StringUtils.capitalize;

public class Utility {

    private static final Logger logger = LoggerFactory.getLogger(Utility.class);

    public static final String PACKAGE_NAME = "packageName";
    public static final String CLASS_NAME = "className";
    public static final String GROUP_ID = "groupId";
    public static final String ARTIFACT_ID = "artifactId";
    public static final String VERSION = "version";
    public static final String PACKAGE = "package";
    public static final String APPLICATION_NAME = "applicationName";
    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String MESSAGE = "message";
    public static final String REPO_NAME = "repoName";
    public static final String JAVA_MAIN_CLASS_OUTPUT_PATH = "javaMainClassPath";
    public static final String JAVA_TEST_CLASS_OUTPUT_PATH = "javaTestClassPath";
    public static final String JAVA_MAIN_CLASS_OUTPUT_NAME = "javaMainClassName";
    public static final String POM_PATH = "pomPath";
    public static final String APPLICATION_PROPERTIES = "applicationProperties";
    public static final String BUILD_GRADLE = "buildGradle";
    public static final String SETTING_GRADLE = "settingGradle";
    public static final String GITLAB_CI_CD_PATH = "gitlabCiCDPath";
    public static final String JENKINS_CI_CD_PATH = "jenkinsCiCDPath";

    private static final Map<String,Object>  feildMap = new HashMap<>();


    public static Map<String,Object> getFeildsMap(RepoSpawnModel repoSpawnModel){

        if (feildMap.isEmpty()){
            Map<String, Object> map = objectToMap(repoSpawnModel);
            feildMap.putAll(map);
            feildMap.put(MESSAGE,"Hello World");
            feildMap.put(PACKAGE_NAME, repoSpawnModel.getGroup()+"."+repoSpawnModel.getArtifact());
            feildMap.put(CLASS_NAME, getClassName(repoSpawnModel.getName()));
            feildMap.put(JAVA_MAIN_CLASS_OUTPUT_PATH,getOutputFilePath(repoSpawnModel));
            feildMap.put(JAVA_TEST_CLASS_OUTPUT_PATH,getOutputFilePath(repoSpawnModel));
            feildMap.put(JAVA_MAIN_CLASS_OUTPUT_NAME,getClassName(repoSpawnModel.getName()));
            feildMap.put(POM_PATH,repoSpawnModel.getArtifact()+"/pom.xml");
            feildMap.put(GITLAB_CI_CD_PATH,repoSpawnModel.getArtifact()+"/.gitlab-ci.yml");
            feildMap.put(JENKINS_CI_CD_PATH,repoSpawnModel.getArtifact()+"/Jenkinsfile");
            feildMap.put(BUILD_GRADLE,repoSpawnModel.getArtifact()+"/build.gradle");
            feildMap.put(SETTING_GRADLE,repoSpawnModel.getArtifact()+"/gradle/settings.gradle");
            feildMap.put(APPLICATION_PROPERTIES,repoSpawnModel.getArtifact()+"/src/main/resources/application.properties");
        }
        return feildMap;
    }

    public static <T> Map<String, Object> objectToMap(T object) {
        Map<String, Object> map = new HashMap<>();

        if (object == null) {
            return map;
        }

        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            try {
                Object value = field.get(object);
                map.put(field.getName(),
                        value);
            } catch (IllegalAccessException e) {
                logger.error("Error generating the map from config");
            }
        }

        return map;
    }


    public static String getOutputFilePath(RepoSpawnModel repoSpawnModel) {
        String replacedString = repoSpawnModel.getGroup().replace(".", "/").toLowerCase();
        return  repoSpawnModel.getArtifact().toLowerCase()
                +"/src/main/java/" +
                replacedString +
                "/" +
                repoSpawnModel.getArtifact().toLowerCase() +
                "/" +
                getClassName(repoSpawnModel.getName()) +
                ".java";
    }

    public static String getOutputTestFilePath(RepoSpawnModel repoSpawnModel) {
        String replacedString = repoSpawnModel.getGroup().replace(".", "/").toLowerCase();
        return  repoSpawnModel.getArtifact().toLowerCase()
                +"/src/test/java/" +
                replacedString +
                "/" +
                repoSpawnModel.getArtifact().toLowerCase() +
                "/" +
                getClassName(repoSpawnModel.getName()) +
                ".java";
    }

    public String getOutputFileName(String repoName) {
        return getClassName(repoName) + ".java";
    }

    public static String getClassName(String repoName) {
        return capitalize(toCamelCase(repoName)+"Application");
    }

    public static String toCamelCase(String input) {
        StringBuilder result = new StringBuilder();
        boolean nextUpperCase = false;

        for (char c : input.toCharArray()) {
            if (Character.isWhitespace(c)) {
                nextUpperCase = true;
            } else {
                if (nextUpperCase) {
                    result.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            }
        }

        return result.toString();
    }

    public static String copyFile(String resourcePath, String generationPath) throws IOException {
        try {
            ClassPathResource resource = new ClassPathResource(resourcePath);
            File destinationFile = new File(generationPath);
            boolean created =  destinationFile.getParentFile().mkdirs();
            FileCopyUtils.copy(resource.getInputStream().readAllBytes(), destinationFile);
        } catch (IOException e) {
            logger.error("Error copying file: {}", e.getMessage());
        }
        return generationPath;
    }
}
