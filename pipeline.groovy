pipeline {
    agent any // can run on any agent ex master(controller)
    /* not needed since we are using maven wrapper
    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M3"
    }*/

    stages {
        
        stage('checkout'){
            steps {
                //GitHub repository to be built with branch
                git url:'https://github.com/sayantan1989/jgsu-spring-petclinic.git', branch :'main'
            }
        }
        stage('Build') {
            steps {
                sh './mvnw clean package'
                // Run Maven on a Unix agent.
                //sh "mvn -Dmaven.test.failure.ignore=true clean package"

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
               success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
    }
}
