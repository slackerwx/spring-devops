pipeline {
  agent {
    docker {
      image 'maven:3-alpine'
      args '-v /root/.m2:/root/.m2'
    }

  }
  stages {
    stage('Test') {
      agent any
      steps {
        sh '''mvn clean test

'''
      }
    }
  }
}