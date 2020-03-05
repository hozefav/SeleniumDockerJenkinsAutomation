//variables
def network='jenkins-%BUILD_NUMBER%'
def seleniumHub='selenium-hub-%BUILD_NUMBER%'
def chrome='chrome-%BUILD_NUMBER%'
def firefox='firefox-%BUILD_NUMBER%'
def containertest='conatinertest-%BUILD_NUMBER%'

pipeline {

   agent any

   stages{
       stage('Build Jar') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }
        stage('Build Image') {
            steps {
                script {
                	app = docker.build("hozefavakanerwala/containertest${BUILD_NUMBER}")
                }
            }
        }
      stage('Setting Up Selenium Grid') {
         steps{
            bat "docker network create ${network}"
            bat "docker run -d -p 4444:4444 --name ${seleniumHub} --network ${network} selenium/hub"
            bat "docker run -d -e HUB_PORT_4444_TCP_ADDR=${seleniumHub} -e HUB_PORT_4444_TCP_PORT=4444 --shm-size 2g --network ${network} --name ${chrome} selenium/node-chrome"
            bat "docker run -d -e HUB_PORT_4444_TCP_ADDR=${seleniumHub} -e HUB_PORT_4444_TCP_PORT=4444 --network ${network} --name ${firefox} selenium/node-firefox"
         }
      }
      stage('Run Test') {
         steps{
            parallel(
               "smoke-module":{
                  bat "docker run --rm -e SELENIUM_HUB=${seleniumHub} -e MODULE=smoketestng.xml -v ${WORKSPACE}/smoke:/usr/share/tag/test-output --network ${network} hozefavakanerwala/containertest${BUILD_NUMBER}"
                  archiveArtifacts artifacts: 'smoke/**', fingerprint: true
               },
               "regression-module":{
                  bat "docker run --rm -e SELENIUM_HUB=${seleniumHub} -e MODULE=testng.xml -v ${WORKSPACE}/regression:/usr/share/tag/test-output  --network ${network} hozefavakanerwala/containertest${BUILD_NUMBER}"
                  archiveArtifacts artifacts: 'regression/**', fingerprint: true
               }
            )
         }
      }
    }
    post{
      always {
         bat "docker rm -vf ${chrome}"
         bat "docker rm -vf ${firefox}"
         bat "docker rm -vf ${seleniumHub}"
         bat "docker network rm ${network}"
      }
   }
}