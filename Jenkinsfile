pipeline {
    agent any

    tools {
        maven 'maven3'
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
                            $class          : 'JacocoPublisher',
                            execPattern     : 'target/jacoco/jacoco.exec',
                            classPattern    : 'target/classes/main',
                            sourcePattern   : 'src/main/java',
                            exclusionPattern: '**/*Test.class'
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

        stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv('My SonarQube Server') {
                    sh "mvn verify sonar:sonar -Dintegration-tests.skip=true -Dmaven.test.failure.ignore=true"
                }
            }
        }

        stage('Quality Gate') {
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

        stage('Deploy') {
            steps {
                configFileProvider([configFile(fileId: 'our_settings', variable: 'SETTINGS')]) {
                    echo "$SETTINGS"
                    sh "mvn -s $SETTINGS deploy -DskipTests"
                }
            }
        }
    }
}