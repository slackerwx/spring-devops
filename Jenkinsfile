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

        stage('Tests') {
            parallel {
                stage("Load Test") {
                    steps {
//                            JMeter or Taurus with Blazer
//                            Applying simulated load to a system and measuring the overall impact on the system
                        echo "TODO: IMPLEMENTAR"
                    }
                }

                stage("Performance Test") {
                    steps {
//                            JMeter
//                            Monitoring and measuring the performance of a system regardless of load
                        echo "TODO: IMPLEMENTAR"
                    }
                }

                stage("Stress Test") {
                    steps {
//                            Subjecting the system to  a load-based failure to be certain how and if that system recovers
                        echo "TODO: IMPLEMENTAR"
                    }
                }

                stage("Integration Test") {
                    steps {
//                        Sometimes you need to have tests to verify that two separate systems – like a database and your app – work together correctly, and that calls for an integration test
//                            RAML
                        echo "TODO: IMPLEMENTAR"
                    }
                }

                stage("Smoke Tests") {
                    steps {
                        echo "TODO: IMPLEMENTAR"
                    }
                }

                stage("Sanity Test") {
                    steps {
                        echo "TODO: IMPLEMENTAR"
                    }
                }

                stage("Functional Test") {
                    steps {
//                        If you would manually test a certain flow of your app in a browser, such as registering an account, you could make that into a functional test.
                        echo "TODO: IMPLEMENTAR"
                    }
                }

                stage("Security Test") {
                    steps {
                        echo "TODO: IMPLEMENTAR"
//                        before_install:
//                        - docker pull karthequian/gruyere:latest
//                        - docker pull gauntlt/gauntlt
//                        - docker run --rm -d -p 8008:8008 karthequian/gruyere:latest
//                        - docker ps -a
//                        - ./scripts/travis-config.sh
//
//                        script:
//                        - cat ./config/cucumber.yml
//                        - docker run -t --rm -v $(pwd):/working -w /working gauntlt/gauntlt ./attacks/hello_world/hello-world.attack
//                        - docker run -t --rm -v $(pwd):/working -w /working gauntlt/gauntlt ./attacks/env-vars/port-env-vars.attack
//                        - docker run -t --rm -v $(pwd):/working -w /working gauntlt/gauntlt ./attacks/ci/xss.attack
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