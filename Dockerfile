FROM openjdk:8-jre-slim

WORKDIR /usr/share/tag

# Add the jar with all the dependencies
# Maven creates container-test.jar in the target folder of my workspace.
# We need this in some location - say - /usr/share/tag folder of the container
#ADD  target/selenium-docker.jar selenium-docker.jar
ADD  target/selenium-docker.jar selenium-docker.jar
ADD  target/selenium-docker-tests.jar selenium-docker-tests.jar
ADD  target/libs libs
ADD testng.xml testng.xml
ADD smoketestng.xml smoketestng.xml

# Command line to execute the test
# ENTRYPOINT ["java", "-cp", "/usr/share/tag/E2EAutomation.jar", "org.testng.TestNG", "-testclass", "validateTitle"]
ENTRYPOINT java -cp selenium-docker.jar:selenium-docker-tests.jar:libs/* -DseleniumHubHost=$SELENIUM_HUB org.testng.TestNG $MODULE
