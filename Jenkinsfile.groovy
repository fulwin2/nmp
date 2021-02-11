//SCANNER_HOME="/tmp/sonar-scanner-4.5.0.2216-linux"
SCANNER_HOME="/home/jenkins/workspace/mvn_build_and_test/sonar-scanner-4.5.0.2216-linux"

pipeline {
    agent {
         label 'java-docker-slave'	
    }	

   /* environment {
        sonar.working.directory = '/tmp'
        DB_ENGINE    = 'sqlite'
    }
   */
    stages {
	
     stage("build & SonarQube analysis") {
	 steps {
              println "$SCANNER_HOME"
              sh "pwd && ls -lsa ."

              withSonarQubeEnv("SonarCube") {
		  println "${env.SONAR_HOST_URL}"
                 //sh 'mvn clean package sonar:sonar'
		  sh """ ${SCANNER_HOME}/bin/sonar-scanner \
                         -Dsonar.qualitygate= Mvntest \
                         -Dsonar.java.binaries=target/classes \
		         -Dsonar.projectBaseDir=${WORKSPACE} \
		         -Dsonar.projectKey=mvn-project \
			 -Dsonar.working.directory=${WORKSPACE}/.scannerwork \
			 -Dsonar.sources=src/main/java
		     """
              }		  
	 }
      }

      stage("Quality Gate"){
	steps {
	      withSonarQubeEnv("SonarCube") {
	         withCredentials([string(credentialsId: 'jenkins1', variable: 'SONAR_TOKEN')]) {
	            sh "printenv"
	            timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
		    }
	        }
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
	
    }
}