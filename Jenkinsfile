pipeline {
    agent any

    environment {
        DOCKERHUB_USER     = "tokiandjack"
        HTEAO_IMAGE        = "${DOCKERHUB_USER}/hteao-07042026"
        PRODUCER_IMAGE     = "${DOCKERHUB_USER}/hteao-kafka-producer"
        CONSUMER_IMAGE     = "${DOCKERHUB_USER}/hteao-kafka-consumer"
        DOCKER_TAG         = "latest"
        CREDS_ID           = "dockerhub-creds"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build hteao JAR') {
            steps {
                bat 'mvn clean install'
            }
        }

        stage('Build kafka-producer JAR') {
            steps {
                dir('kafka-producer') {
                    bat 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build kafka-consumer JAR') {
            steps {
                dir('kafka-consumer') {
                    bat 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Docker Build') {
            steps {
                // hteao main app
                bat "docker build -t %HTEAO_IMAGE%:%DOCKER_TAG% ."

                // kafka-producer
                bat "docker build -t %PRODUCER_IMAGE%:%DOCKER_TAG% ./kafka-producer"

                // kafka-consumer
                bat "docker build -t %CONSUMER_IMAGE%:%DOCKER_TAG% ./kafka-consumer"
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: "${CREDS_ID}",
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS')]) {

                    bat 'echo %DOCKER_PASS%| docker login -u %DOCKER_USER% --password-stdin'

                    // Push hteao
                    bat "docker push %HTEAO_IMAGE%:%DOCKER_TAG%"

                    // Push kafka-producer
                    bat "docker push %PRODUCER_IMAGE%:%DOCKER_TAG%"

                    // Push kafka-consumer
                    bat "docker push %CONSUMER_IMAGE%:%DOCKER_TAG%"

                    bat 'docker logout'
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                bat 'docker-compose down || echo nothing to stop'
                bat 'docker-compose up -d'
                bat 'ping -n 20 127.0.0.1 > nul'
                bat 'docker ps'
            }
        }
    }

    post {
        success {
            echo "SUCCESS — all 3 images deployed!"
            echo "hteao-app      → tokiandjack/hteao-07042026:latest"
            echo "kafka-producer → tokiandjack/hteao-kafka-producer:latest"
            echo "kafka-consumer → tokiandjack/hteao-kafka-consumer:latest"
        }
        failure {
            echo "BUILD FAILED — check console above"
        }
        always {
            bat 'docker image prune -f || echo done'
        }
    }
}
