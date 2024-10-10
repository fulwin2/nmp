//SCANNER_HOME="/tmp/sonar-scanner-4.5.0.2216-linux"
SCANNER_HOME="/home/jenkins/workspace/mvn_build_and_test/sonar-scanner-4.5.0.2216-linux"
@Library('selecttag@master') _
//Comment

pipeline {
  agent {
    label 'jenkins-slave'
  }

  /* environment {
  sonar.working.directory = '/tmp'
  DB_ENGINE    = 'sqlite'
}
*/
//
stages {
        stage('Fetch Tags') {
            steps {
                script {
                    // Fetch the Git tags dynamically using the shared library
                    def availableTags = getGitHubTags.fetchGitTags("https://github.com/fulwin2/nmp")

                    // If no tags are found, provide a default option
                    availableTags = availableTags ?: ['latest']

                    // Prompt the user to select a tag from the dynamically fetched list
                    env.TAG_NAME = input message: 'Select a Git tag to build from:',
                                        ok: 'Proceed',
                                        parameters: [choice(name: 'TAG_NAME', choices: availableTags, description: 'Git Tag')]
                }
            }
        }
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

