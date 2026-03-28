pipeline {
    agent any

    tools {
        maven 'maven3'       // tên Maven tool bạn đã cấu hình
    }

    environment {
        IMAGE_NAME = "hoangvudang206/shoppingcart"
        IMAGE_TAG = "latest"
    }

    stages {

        stage('Checkout') {
            steps {
                echo "Pulling source code..."
                git branch: 'main', url: 'https://github.com/Hoang-Vuu/week2_assignment.git'
            }
        }

        stage('Build with Maven') {
            steps {
                echo "Running mvn clean package..."
                bat "\"${MAVEN_HOME}\\bin\\mvn\" clean package -DskipTests"
            }
        }

        stage('Run Unit Tests') {
            steps {
                echo "Running mvn test..."
                bat "\"${MAVEN_HOME}\\bin\\mvn\" test"
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "Building Docker image..."
                bat "docker build -t %IMAGE_NAME%:%IMAGE_TAG% ."
            }
        }

        stage('Docker Login') {
            steps {
                echo "Logging into Docker Hub..."
                withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    bat """
                        echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
                    """
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                echo "Pushing Docker image to Docker Hub..."
                bat "docker push %IMAGE_NAME%:%IMAGE_TAG%"
            }
        }
    }

    post {
        always {
            junit(testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true)
            recordCoverage tools: [[parser: 'JACOCO']]
        }
        success {
            echo "🎉 PIPELINE SUCCESS: Build + Test + Docker Push completed!"
        }
        failure {
            echo "❌ PIPELINE FAILED — Check logs."
        }
    }
}
