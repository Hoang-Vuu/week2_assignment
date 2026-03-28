pipeline {
    agent any

    environment {
        DOCKERHUB = credentials('dockerhub') 
        IMAGE_NAME = "hoangvudang206/shoppingcart"
    }

    stages {

        stage('Checkout') {
            steps {
                echo "Checking out source code..."
                checkout scm
            }
        }

        stage('Build Maven') {
            steps {
                echo "Building project with Maven..."
                sh "mvn clean package -DskipTests"
            }
        }

        stage('Run Unit Tests') {
            steps {
                echo "Running tests..."
                sh "mvn test"
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "Building Docker image..."
                sh "docker build -t ${IMAGE_NAME}:latest ."
            }
        }

        stage('Docker Login') {
            steps {
                echo "Logging into Docker Hub…"
                sh "echo ${DOCKERHUB_PSW} | docker login -u ${DOCKERHUB_USR} --password-stdin"
            }
        }

        stage('Push Docker Image') {
            steps {
                echo "Pushing image to Docker Hub..."
                sh "docker push ${IMAGE_NAME}:latest"
            }
        }
    }

    post {
        success {
            echo "Pipeline completed successfully!"
        }
        failure {
            echo "Pipeline failed. Please check logs."
        }
    }
}
