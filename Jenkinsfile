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
              slackSend channel: '#ci', color: 'good', failOnError: true, message: "Build succeeded: ${env.JOB_NAME} ${env.BUILD_NUMBER}", teamDomain: 'book-spot', tokenCredentialId: '94413a97-7f5e-456b-aa13-d8abf57fca3d'
          }

          failure {
              slackSend channel: '#ci', color: 'danger', failOnError: true, message: "Build failed: ${env.JOB_NAME} ${env.BUILD_NUMBER}", teamDomain: 'book-spot', tokenCredentialId: '94413a97-7f5e-456b-aa13-d8abf57fca3d'
          }
     }
}