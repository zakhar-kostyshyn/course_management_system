pipeline {
    agent { dockerfile true }
    stages {
        stage('Test') {
            steps {
                sh './gradlew test'
            }
            post {
                always {
                    junit '**/build/test-results/test/TEST-*.xml'
                }
            }
        }
    }
}