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
        stage('Sonar scan execution'){
            steps{
                sh 'mvn verify sonar:sonar -Dsonar.host.url=http://sonarqube:9000 -Dintegration-tests.skip=true -Dmaven.test.failure.ignore=true'
            }
        }
        stage('Sonar scan result check') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    retry(3) {
                        script {
                            def qg = waitForQualityGate()
                            if (qg.status != 'OK') {
                                error "Pipeline aborted due to quality gate failure: ${qg.status}"
                            }
                        }
                    }
                }
            }
        }

    }
}