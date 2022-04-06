pipeline {
    environment {
        registry = "zakhar0kostyshyn/cms_app"
        registryCredential = 'docker-hub'
        dockerImage = ''
    }
    agent any
    stages {
        stage('Run tests') {
            agent {
                docker {
                    image: 'gradle:6.7-jdk11'
                }
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
                sh "docker rmi $registry:$BUILD_NUMBER"
            }
        }
    }
}