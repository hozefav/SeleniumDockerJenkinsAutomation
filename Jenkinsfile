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
	stage('CLI workaround') {
/* Workaround to address issue with credentials stored in Jenkins not
* being passed correctly to the docker registry
* - ref https://issues.jenkins-ci.org/browse/JENKINS-38018 */
		steps {
withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'dockerhub',
usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
bat 'docker login -u $USERNAME -p $PASSWORD https://index.docker.io/v1/'
}
}
}
        stage('Push Image') {
            steps {
                script {
			        docker.withRegistry('https://registry.hub.docker.com', 'dockerhub') {
			        	app.push("${BUILD_NUMBER}")
			            app.push("latest")
			        }
                }
            }
        }      
    }
}
