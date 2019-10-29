pipeline {
     agent any
     stages {
          stage("Build") {
               agent {
                    docker {
                         image 'openjdk:8-jdk-alpine'
                    }
               }
               steps {
                    sh './gradlew build -x test'
                    sh 'echo "Build completed"'
               }
          }
          stage("Test") {
               agent {
                    docker {
                         image 'openjdk:8-jdk-alpine'
                    }
               }
               steps {
                    sh './gradlew test'
                    sh 'echo "Testing completed"'
               }
          }
     }
     post {
          success {
              slackSend color: 'good', teamDomain: 'book-spot', tokenCredentialId: '94413a97-7f5e-456b-aa13-d8abf57fca3d'
          }

          failure {
              slackSend color: 'danger', teamDomain: 'book-spot', tokenCredentialId: '94413a97-7f5e-456b-aa13-d8abf57fca3d'
          }
     }
}