install:
	mvn clean install
	mvn test
	mvn clean javafx:run
start:
	mvn clean javafx:run
test:
	mvn test