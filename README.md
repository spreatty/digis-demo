# DIGIS demo project
## Building and running
Before building you must install Maven and JDK at least 1.8. Be sure that you have MySQL up and running. Configure DB host, username and password in application.properties.\
\
Now you can build with 'mvn clean package' and then run with 'java -jar target/\*.jar', this will start server on localhost:8080.
## Using
All endpoints use HAL format.\
GET /user returns list of all users\
POST /user creates and returns user\
PUT /user/{id} modifies and returns specific user\
GET /user/{id} returns specific user
