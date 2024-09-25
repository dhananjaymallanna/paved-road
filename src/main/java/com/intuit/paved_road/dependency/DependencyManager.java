package com.intuit.paved_road.dependency;

import java.util.List;

public class DependencyManager {
    List<DependencyModel> dependencies;

    public List<DependencyModel> getDependencies() {
        return List.of(
        new DependencyModel("org.springframework.boot", "spring-boot-starter-web", "3.1.4"),
                new DependencyModel("org.springframework.boot", "spring-boot-starter-data-jpa", "3.1.4"),
                new DependencyModel("org.springframework.boot", "spring-boot-starter-security", "3.1.4"),
                new DependencyModel("org.springframework.boot", "spring-boot-starter-test", "3.1.4"),
                new DependencyModel("org.springframework.boot", "spring-boot-starter-thymeleaf", "3.1.4"),
                new DependencyModel("org.springframework.boot", "spring-boot-starter-jdbc", "3.1.4"),
                new DependencyModel("org.springframework.boot", "spring-boot-starter-data-redis", "3.1.4"),
                new DependencyModel("org.springframework.boot", "spring-boot-starter-actuator", "3.1.4"),
                new DependencyModel("org.springframework.boot", "spring-boot-starter-mail", "3.1.4"),
                new DependencyModel("org.springframework.boot", "spring-boot-starter-validation", "3.1.4"),
                new DependencyModel("org.springframework.boot", "spring-boot-starter-amqp", "3.1.4"),
                new DependencyModel("org.springframework.boot", "spring-boot-starter-kafka", "3.1.4"),
                new DependencyModel("org.springframework.boot", "spring-boot-starter-cloud-stream", "3.1.4"),
                new DependencyModel("org.springframework.boot", "spring-boot-starter-cloud-config", "3.1.4"));
    }
}
