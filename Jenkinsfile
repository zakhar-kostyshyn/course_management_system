pipeline {
    environment {
        registry = "zakhar0kostyshyn/cms_app"
        registryCredential = 'docker-hub'
        dockerImage = ''
        ec2SSHSecretFile = credentials('cms-pem')
    }
    agent any
    stages {
        stage('Run tests') {
            agent {
                docker { image 'gradle:6.7-jdk11' }
            }
            steps {
                sh './gradlew test'
            }
            post {
                always {
                    junit '**/build/test-results/test/TEST-*.xml'
                }
            }
        }
        stage('Cloning Git') {
            steps {
                git 'https://github.com/zakhar-kostyshyn/course_management_system.git'
            }
        }
        stage('Building Image') {
            steps{
                script {
                    dockerImage = docker.build registry + ":latest"
                }
            }
        }
        stage('Deploy Image') {
            steps{
                script {
                    docker.withRegistry( '', registryCredential) {
                        dockerImage.push()
                    }
                }
            }
        }
        stage('Remove Unused docker image') {
            steps{
                sh "docker rmi $registry:latest"
            }
        }
        stage('Deploy to AWS') {
            sh 'ssh -i "$ec2SSHSecretFile" ubuntu@ec2-3-70-183-251.eu-central-1.compute.amazonaws.com docker rm -f cms_app'
            sh 'ssh -i "$ec2SSHSecretFile" ubuntu@ec2-3-70-183-251.eu-central-1.compute.amazonaws.com docker rm -f cms_db'
            sh 'ssh -i "$ec2SSHSecretFile" ubuntu@ec2-3-70-183-251.eu-central-1.compute.amazonaws.com docker rmi -f zakhar0kostyshyn/cms_app'
            sh 'ssh -i "$ec2SSHSecretFile" ubuntu@ec2-3-70-183-251.eu-central-1.compute.amazonaws.com docker pull zakhar0kostyshyn/cms_app:latest'
            sh 'ssh -i "$ec2SSHSecretFile" ubuntu@ec2-3-70-183-251.eu-central-1.compute.amazonaws.com docker run -d --name cms_db --network cms-network -p 5432:5432 -e POSTGRES_USER=root -e POSTGRES_PASSWORD=root -e POSTGRES_DB=cms_db postgres:14.1-alpine'
            sh 'ssh -i "$ec2SSHSecretFile" ubuntu@ec2-3-70-183-251.eu-central-1.compute.amazonaws.com docker run -d --name cms_app --network cms-network -p 8882:8080 zakhar0kostyshyn/cms_app:latest'
        }
    }
}