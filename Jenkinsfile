pipeline {
    agent any

    tools {
        maven 'maven3'
    }

    options {
        skipStagesAfterUnstable()
    }

    stages {
        stage('Build and Test') {
            steps {
                echo 'Pulling...' + env.BRANCH_NAME
                sh 'mvn -Dintegration-tests.skip=true clean package'
            }

            post {
                always {
                    junit 'target/surefire-reports/TEST*.xml'
                    archive 'target/*.jar'
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
                            reportTitles         : "API Report",
                            reportName           : "API Report"
                    ])
                }
            }
        }

        stage('Integration tests') {
            steps {
                sh "mvn verify -Dunit-tests.skip=true"
            }
        }

        stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv('My SonarQube Server') {
                    sh "mvn verify sonar:sonar -Dintegration-tests.skip=true -Dunit-tests.skip=true"
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
                    sh "mvn -s $SETTINGS -Dintegration-tests.skip=true -Dintegration-tests.skip=true deploy"
                }
            }
        }
    }
}