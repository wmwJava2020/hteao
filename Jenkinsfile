pipeline {
    agent any

    environment {
        DOCKERHUB_USER = "tokiandjack"
        IMAGE          = "${DOCKERHUB_USER}/hteao-07042026"
        DOCKER_TAG     = "tokiandjack"
        CREDS_ID       = "dockerhub-creds"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                bat 'mvn clean install'
            }
        }

        stage('Docker Build') {
            steps {
                bat "docker build -t %IMAGE%:%DOCKER_TAG% ."
                bat "docker tag %IMAGE%:%DOCKER_TAG% %IMAGE%:latest"
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: "${CREDS_ID}",
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS')]) {

                    bat 'echo %DOCKER_PASS%| docker login -u %DOCKER_USER% --password-stdin'
                    bat "docker push %IMAGE%:%DOCKER_TAG%"
                    bat "docker push %IMAGE%:latest"
                    bat 'docker logout'
                }
            }
        }
    }

    post {
        success {
            echo "SUCCESS — tokiandjack/hteao-07042026 pushed to Docker Hub!"
        }
        failure {
            echo "BUILD FAILED"
        }
        always {
            bat 'docker image prune -f || echo done'
        }
    }
}
