pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'gradle build'
            }
        }
        stage('Test') {
            steps {
                sh 'gradle test'
            }
        }
        stage('Deploy') {
            steps {
                sh 'gradle bootJar'
                sh 'docker build -t ${name}-image .'
                sh 'docker push ${name}-image'
            }
        }
    }
}