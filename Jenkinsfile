pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }

    }
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                    step([
                        $class           : 'JacocoPublisher',
                        execPattern      : 'target/jacoco/jacoco.exec',
                        classPattern     : 'target/classes/main',
                        sourcePattern    : 'src/main/java',
                        exclusionPattern : '**/*Test.class'
                    ])
                    publishHTML([
                        allowMissing         : false,
                        alwaysLinkToLastBuild: false,
                        keepAll              : true,
                        reportDir            : 'target/site/jacoco',
                        reportFiles          : 'index.html',
                        reportTitles         : "API Documentation",
                        reportName           : "API Documentation"
                    ])
                }
            }
        }
    }
}