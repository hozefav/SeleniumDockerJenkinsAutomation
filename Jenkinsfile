pipeline {
    agent any
    stages {
        stage('Build Jar') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }
        stage('Build Image') {
            steps {
                script {
                	app = docker.build("hozefavakanerwala/containertest")
                }
            }
        }
 
        stage('Push Image') {
            steps {
                script {
			      // docker.withRegistry('https://registry.hub.docker.com', 'dockerhub')
			withDockerRegistry([ "hozefavakanerwala": "0ca476f3-44b1-4db5-8444-94fa537a640a", url: "" ]) {			
			        	app.push("${BUILD_NUMBER}")
			            app.push("latest")
			        }
                }
            }
        }      
    }
}
