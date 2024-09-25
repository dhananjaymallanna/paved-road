image: openjdk:11-jdk-alpine

stages:
  - build
  - test
  - deploy

build:
  stage: build
  script:
    - ./gradlew build

test:
  stage: test
  script:
    - ./gradlew test

deploy:
  stage: deploy
  script:
    - ./gradlew bootJar
    - docker build -t ${name} .
    - docker push ${name}
  only:
    - master


.gitlab-ci.yaml