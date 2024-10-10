//SCANNER_HOME="/tmp/sonar-scanner-4.5.0.2216-linux"
SCANNER_HOME="/home/jenkins/workspace/mvn_build_and_test/sonar-scanner-4.5.0.2216-linux"
@Library('select_tags') _
//Comment
pipeline {
  agent {
    label 'jenkins-slave'
  }
parameters {
        choice(name: 'TAG_VERSION', choices: getGitHubTagsLocal("https://github.com/fulwin2/nmp/tags"), description: 'Choose the Git tag version to build')
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

  stage('Archive Artifacts') {
    steps {
      // Archive the artifact from the target/ directory
      archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: false
    }
  }
}
}

def getGitHubTagsLocal() {
  def tags = getGitHubTags.fetchGitTags("https://github.com/fulwin2/nmp")
}
