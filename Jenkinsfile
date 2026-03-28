pipeline {
    agent any

    tools {
        // Maven tool name (must match Global Tool Configuration)
        maven 'maven'
    }

    environment {
        DOCKERHUB = credentials('dockerhub')
        IMAGE_NAME = "hoangvudang206/shoppingcart"
    }

    stages {

        stage('Checkout Code') {
            steps {
                echo "Checking out repository..."
                checkout scm
            }
        }

        stage('Build with Maven') {
            steps {
                echo "Building project with Jenkins Maven tool..."
                bat "\"${MAVEN_HOME}\\bin\\mvn\" clean package -DskipTests"
            }
        }

        stage('Run Unit Tests') {
            steps {
                echo "Running unit tests..."
                bat "\"${MAVEN_HOME}\\bin\\mvn\" test"
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "Building Docker image..."
                bat "docker build -t %IMAGE_NAME%:latest ."
            }
        }

        stage('Docker Login') {
            steps {
                echo "Logging into Docker Hub..."
                bat """
                echo ${DOCKERHUB_PSW} | docker login -u ${DOCKERHUB_USR} --password-stdin
                """
            }
        }

        stage('Push Docker Image') {
            steps {
                echo "Pushing Docker image to Docker Hub..."
                bat "docker push %IMAGE_NAME%:latest"
            }
        }
    }

    post {
        success {
            echo "🎉 Pipeline completed successfully!"
        }
        failure {
            echo "❌ Pipeline failed. Please check logs."
        }
    }
}
