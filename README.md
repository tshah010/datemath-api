# Getting Started with DateMath-API

### To Run Locally
In the project directory

`mvn spring-boot:run`\
Install the dependencies listed in `pom.xml` and starts the app in the development mode.
Open [http://localhost:8080](http://localhost:8080) to view it in the browser.


`mvn test`\
Run all the Unit Tests.

`mvn test jacoco:report`\
Run the Unit Tests and generates test coverage report `target\site\jacoco\index.html`

`mvn clean package`\
Create a new .jar file under `target` folder

`java -jar target/datemath-api-1.0.0.jar --trace`\
Run the .jar file. You can access Swagger Documents [here.](http://localhost:8080/datemath/api/swagger-ui/index.html?configUrl=/datemath/api/v3/api-docs/swagger-config)
Click [here](http://localhost:8080/datemath/api/v1/calculate-before-after?daysOrHours=5&unitOfTime=1&operator=1&userDateTime=02-13-1974) to test API endpoint after the application is running

### To configure new provisioned EC2 instance
`ssh -i "<path to local .pem file>" ec2-user@ec2-13-59-129-251.us-east-2.compute.amazonaws.com`\
SSH into the AWS EC2 server.

`yum search java | grep -i --color JDK`\
Search for available JDK packages in Yum

Download and Install JDK 15
- `curl -0 https://download.java.net/openjdk/jdk15/ri/openjdk-15+36_linux-x64_bin.tar.gz --output openjdk-15+36_linux-x64_bin.tar.gz`\
- `tar zxvf openjdk-15+36_linux-x64_bin.tar.gz`\
- `sudo mv jdk-15/ /usr/local/`

Setup the JDK Path
- `sudo vi /etc/profile.d/jdk15.sh`
    - `export JAVA_HOME=/usr/local/jdk-15`
    - `export PATH=$PATH:$JAVA_HOME/bin`
- `source /etc/profile.d/jdk15.sh`
- `java -version`

### To Deploy to AWS
`ssh -i "<path to local .pem file>" ec2-user@ec2-13-59-129-251.us-east-2.compute.amazonaws.com`\
SSH into the AWS EC2 server.

`kill -9 $(lsof -t -i:8080)`\
Stop running the existing .jar file and back it up

`scp -i "<path to local .pem file>" target/<jar file name> ec2-user@ec2-13-59-129-251.us-east-2.compute.amazonaws.com:/home/ec2-user`\
Copy the .jar file from project folder to AWS EC2 server

`nohup java -jar datemath-api-1.0.0.jar > output.log 2>&1 &`\
Run the .jar file in background


### Helpful Resources

- [Download OpenJDK 15](https://jdk.java.net/java-se-ri/15)
- [Spring Docs Demo](https://springdoc.org/#demos)



