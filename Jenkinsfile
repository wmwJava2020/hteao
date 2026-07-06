pipeline {
    agent any

    environment {
        DOCKERHUB_USER   = "tokiandjack"
        HTEAO_IMAGE      = "${DOCKERHUB_USER}/hteao-07042026"
        PRODUCER_IMAGE   = "${DOCKERHUB_USER}/hteao-kafka-producer"
        CONSUMER_IMAGE   = "${DOCKERHUB_USER}/hteao-kafka-consumer"
        DOCKER_TAG       = "latest"
        CREDS_ID         = "dockerhub-creds"
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
                bat "docker build -t %HTEAO_IMAGE%:%DOCKER_TAG% ."
                bat "docker build -t %PRODUCER_IMAGE%:%DOCKER_TAG% ./kafka-producer"
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
                    bat "docker push %HTEAO_IMAGE%:%DOCKER_TAG%"
                    bat "docker push %PRODUCER_IMAGE%:%DOCKER_TAG%"
                    bat "docker push %CONSUMER_IMAGE%:%DOCKER_TAG%"
                    bat 'docker logout'
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                // Force remove existing containers
                bat 'docker rm -f hteao-zookeeper     || echo not found'
                bat 'docker rm -f hteao-kafka         || echo not found'
                bat 'docker rm -f hteao-mysql         || echo not found'
                bat 'docker rm -f hteao-app           || echo not found'
                bat 'docker rm -f hteao-kafka-producer|| echo not found'
                bat 'docker rm -f hteao-kafka-consumer|| echo not found'

                // Clean up networks
                bat 'docker network rm hteao-pipeline_hteao-network || echo no network'
                bat 'docker network rm HTEAO_hteao-network          || echo no network'

                // Start Zookeeper first — needs time to be ready
                bat 'docker-compose up -d zookeeper'
                bat 'ping -n 16 127.0.0.1 > nul'

                // Start Kafka — needs Zookeeper ready
                bat 'docker-compose up -d kafka'
                bat 'ping -n 26 127.0.0.1 > nul'

                // Start all remaining services
                bat 'docker-compose up -d'
                bat 'ping -n 11 127.0.0.1 > nul'

                // Show running containers
                bat 'docker ps'
            }
        }
    }

    post {
        success {
            echo "BUILD ${BUILD_NUMBER} SUCCESS"
            echo "hteao-app      → port 1972"
            echo "kafka-producer → port 8082"
            echo "kafka-consumer → port 8083"
            echo "kafka broker   → port 9092"
        }
        failure {
            echo "BUILD ${BUILD_NUMBER} FAILED"
        }
        always {
            bat 'docker image prune -f || echo done'
        }
    }
}
