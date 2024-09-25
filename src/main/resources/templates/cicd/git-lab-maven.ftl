image: maven:latest

stages:
  - build
  - test
  - deploy

build:
  stage: build
  script:
    - mvn clean package

test:
  stage: test
  script:
    - mvn test

deploy:
  stage: deploy
  script:
    - docker build -t ${name} .
    - docker push ${name}
  only:
    - master