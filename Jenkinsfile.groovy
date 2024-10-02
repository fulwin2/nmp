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
    stage("Build") {
      steps {
        git url: 'https://github.com/cyrille-leclerc/multi-module-maven-project'
        withMaven {
          sh "mvn compile"
        } // withMaven will discover the generated Maven artifacts, JUnit Surefire & FailSafe reports and FindBugs reports
      }
    }
  }
}
