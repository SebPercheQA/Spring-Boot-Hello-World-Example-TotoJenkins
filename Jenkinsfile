pipeline {
    agent any
    
    tools {
        maven 'localMaven'
    }
    
    stages {
        
        stage('Checkout') {
            steps {
                echo "-=- Récupération du projet java sur git distant -=-"
                git url: 'https://github.com/SebPercheQA/Spring-Boot-Hello-World-Example-TotoJenkins.git'
            }
        }
        
        stage('Package') {
            steps {
                echo "-=- packaging project - équivalent du job package -=-"
                sh 'mvn clean package -Dmaven.test.skip=true'
            }
            
        }

        stage('Test') {
            steps {
                echo "-=- Test project -goal maven test -=-"
                sh 'mvn clean test'
            }
            
            post {
                success {
                    junit 'target/surefire-reports/*.xml' // si BUILD SUCCESS, on fait un rapport de test
                }
            }
        }     
        
        stage('Sonarqube Scanner') {
            environment {
                SCANNER_HOME = tool 'sonarqube'
                ORGANIZATION = "EQL"
                PROJECT_NAME = "my_boot_01"
            }
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh '''$SCANNER_HOME/bin/sonar-scanner \
                    -Dsonar.java.sources=src \
                    -Dsonar.java.binaries=target \
                    -Dsonar.projectKey=$PROJECT_NAME \
                    -Dsonar.language=java \
                    -Dsonar.sourceEncoding=UTF-8'''
                  }
            }
        }
      
        stage('Continuous delivery') {
          steps {
             script {
              sshPublisher(
               continueOnError: false, failOnError: true,
               publishers: [
                sshPublisherDesc(
                 configName: "docker-host",
                 verbose: true,
                 transfers: [
                  sshTransfer(
                   sourceFiles: "target/*.jar",
                   removePrefix: "/target",
                   remoteDirectory: "",
                   execCommand: """
                    sudo mv demo-0.0.1-SNAPSHOT.jar /home/vagrant/project;
                    cd project;
                    sudo docker build -t springbootapp1 . ;
                    docker tag springbootapp1 sebpercheqa/springbootapp1:1.0
                    docker push sebpercheqa/springbootapp1:1.0 """
                  )
                 ])
               ])
             }
          }
        }
        
        stage('Continuous deployment') {
          steps {
             script {
              sshPublisher(
               continueOnError: false, failOnError: true,
               publishers: [
                sshPublisherDesc(
                 configName: "docker-host",
                 verbose: true,
                 transfers: [
                  sshTransfer(
                   sourceFiles: "",
                   removePrefix: "",
                   remoteDirectory: "",
                   execCommand: """
                    sudo docker stop \$(docker ps -a -q);
                    sudo docker rm \$(docker ps -a -q);
                    sudo docker rmi -f \$(docker images -a -q);
                    sudo docker run -d -p 8080:8080 sebpercheqa/springbootapp1:1.0; """
                  )
                 ])
               ])
             }
          }
        }

       stage('Checkout Selenium') {
            steps {
                echo "-=- Checkout project -=-"
                git url: 'https://github.com/SebPercheQA/example-springboot-automation-test-selenium.git'
            }
        }
        
        stage('Selenium Test Job') {
            steps {
                 build job: 'projet-selenium' 
            }
        }
    }
}
