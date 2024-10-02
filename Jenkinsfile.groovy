//SCANNER_HOME="/tmp/sonar-scanner-4.5.0.2216-linux"
SCANNER_HOME="/home/jenkins/workspace/mvn_build_and_test/sonar-scanner-4.5.0.2216-linux"
//Comment
pipeline {
  agent {
    label 'java-docker-slave'
  }

  /* environment {
  sonar.working.directory = '/tmp'
  DB_ENGINE    = 'sqlite'
}
*/
//
stages {


  stage("NMP") {
    steps {

      script {
        try {
          sh "mvn compile"
          sh "mvn package"
          println "SUCCESS: ${BUILD_NUMBER}"
          } catch (Exception e){
            testPassed = false
          }
        }

      }
    }

  }
}
