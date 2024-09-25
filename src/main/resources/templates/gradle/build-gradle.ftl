plugins {
    id 'java'
    id '${packaging}'
<#if type == "spring-boot">
    id 'org.springframework.boot' version '3.3.4'
    id 'io.spring.dependency-management' version '1.1.6'
</#if>
}

group = '${group}'
version = '${version}'

java {
    toolchain {
    languageVersion = JavaLanguageVersion.of(${javaVersion})
    }
}

repositories {
    mavenCentral()
}

dependencies {
<#if type == "java">
    testImplementation platform('org.junit:junit-bom:5.10.0')
    testImplementation 'org.junit.jupiter:junit-jupiter'
</#if>

<#if type == "spring-boot">
    implementation 'org.springframework.boot:spring-boot-starter-web'
    providedRuntime 'org.springframework.boot:spring-boot-starter-tomcat'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
</#if>
}

test {
    useJUnitPlatform()
}