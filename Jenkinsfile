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
}