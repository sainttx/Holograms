pipeline {
    agent any
    tools {
        jdk 'Java 8'
    }
    stages {
        stage ('Build') {
            steps {
              sh 'gradle clean build -PbuildNumber=${BUILD_NUMBER} -Pbranch=${GIT_BRANCH}'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'Holograms/build/libs/*.jar', followSymlinks: false
                    javadoc javadocDir: 'Holograms-API/build/docs/javadoc', keepAll: true
                }
            }
        }
    }
}