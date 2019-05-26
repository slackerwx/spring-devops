// http://jesseyates.com/2018/09/16/dockerizing-jenkins-maven-builds.html
DOCKER_MAVEN_IMAGE = 'maven:3-alpine'
// Bind workspace m2 repo to not download internet too many times.
// New builds will have to download jars once, but should have minimal thrash for later runs.
// We don't bind $HOME/.m2 to ensure independence across builds
DOCKER_MAVEN_ARGS = '-v $HOME/.m2/builds/$BRANCH_NAME:/root/.m2 -u 0:0'


pipeline {
    agent {
        docker {
            image DOCKER_MAVEN_IMAGE
            args DOCKER_MAVEN_ARGS
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

    }
}