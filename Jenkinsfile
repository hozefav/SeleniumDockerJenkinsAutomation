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
		    withCredentials([usernamePassword( credentialsId: 'dockerhub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    docker.withRegistry('', 'dockerhub') {
                    bat "docker login -u ${USERNAME} -p ${PASSWORD}"
                    app.push("${env.BUILD_NUMBER}")
                    app.push("latest")
			        }
                }
            }
        }        
    }
}
}
