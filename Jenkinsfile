pipeline {
    agent any

    stage('Build'){
        steps {
            sh './gradlew build'
            archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
        }
    }

    stage('Test'){
        echo 'Test placeholder'
    }

    stage('Deploy'){
        echo 'Deploy placeholder'
    }
}