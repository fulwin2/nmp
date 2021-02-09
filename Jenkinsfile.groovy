SCANNER_HOME
pipeline {
	agent {
	     label 'java-docker-slave'	
	}	
     
	stages {
		
     stage("build & SonarQube analysis") {
          node {
              withSonarQubeEnv('SonarCube') {
                 sh 'mvn clean package sonar:sonar'
				 sh '''$SCANNER_HOME/bin/sonar-scanner \
                       -Dsonar.java.binaries=target/classes \
					   -Dsonar.projectBaseDir=/tmp \
					   -Dsonar.projectKey=/tmp \
                       -Dsonar.sources=src/main/java'''
              }
          }
      }

      stage("Quality Gate"){
          timeout(time: 1, unit: 'HOURS') {
              def qg = waitForQualityGate()
              if (qg.status != 'OK') {
                  error "Pipeline aborted due to quality gate failure: ${qg.status}"
              }
          }
      }
		
	}
}
