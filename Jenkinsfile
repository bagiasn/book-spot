pipeline {
     agent any
     stages {
          stage("Build") {
               agent {
                    docker {
                         image 'gradle:5.2.1-jdk8-alpine'
                    }
               }
               steps {
                    sh './gradle build -x test'
                    sh 'echo "Build completed"'
               }
          }
          stage("Test") {
               agent {
                    docker {
                         image 'gradle:5.2.1-jdk8-alpine'
                    }
               }
               steps {
                    sh './gradle test'
                    sh 'echo "Testing completed"'
               }
          }
     }
     post {
          success {
              slackSend channel: '#ci', color: 'good', failOnError: true, message: "Build succeeded: '${env.BRANCH_NAME}' (<${env.BUILD_URL}|${env.BUILD_NUMBER}>)", teamDomain: 'book-spot', tokenCredentialId: '94413a97-7f5e-456b-aa13-d8abf57fca3d'
          }

          failure {
              slackSend channel: '#ci', color: 'danger', failOnError: true, message: "Build failed: '${env.BRANCH_NAME}' (<${env.BUILD_URL}|${env.BUILD_NUMBER}>)", teamDomain: 'book-spot', tokenCredentialId: '94413a97-7f5e-456b-aa13-d8abf57fca3d'
          }
     }
}