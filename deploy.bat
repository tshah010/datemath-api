del /Q target
echo "Deleted target folder"

mvn clean package
echo "Generated new .jar file"

scp -i "c:\users\tshah\tushar2020-aws-kp.pem" remove_existing_app.sh ec2-user@ec2-13-59-129-251.us-east-2.compute.amazonaws.com:/home/ec2-user
echo "Copied latest 'remove_existing_app.sh' file from local machine to ec2 instance"

scp -i "c:\users\tshah\tushar2020-aws-kp.pem" target/datemath-api-0.0.1-SNAPSHOT.jar ec2-user@ec2-13-59-129-251.us-east-2.compute.amazonaws.com:/home/ec2-user
echo "Copied jar file from local machine to ec2 instance"

echo "Connecting to ec2 instance and starting server using java -jar command"
ssh -i "c:\users\tshah\tushar2020-aws-kp.pem" ec2-user@ec2-13-59-129-251.us-east-2.compute.amazonaws.com ./remove_existing_app.sh