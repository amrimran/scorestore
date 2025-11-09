# scorestore
Music Score Store using Java Spring Boot using Component-Based Approach

Purpose: To design a web-based platform that allows customers to browse, purchase, and access digital music scores.

Project Details Brief:
- Actor(s): User, Admin
- Module(s): Authentication Module, Account Management Module, Music Score Access Module, Feedback Module & Music Score Management Module
- Use Case Diagram:

-----------

Implementation Details:



-----------

Tools Needed to Run Locally:
1. OpenJDK Java 17
2. SpringBoot3.4.1
3. Thymeleaf
4. MySQL
5. Maven
6. VS Code

Installation Guide

1. Clone this repo into your local machine.
2. Ensure all the these tools (JDK 17, Apache Maven, MySQL) are there and working.
4. Open MySQL through Command Prompt(Windows)/Terminal(MacOS) using command _mysql -u root -p_ respectively based on OS used.
5. Upon entering MySQL command-cine client mode, create a database named _scorestore_.
      _CREATE DATABASE scorestore;_
6. Update the Update the application.properties file located in src/main/resources with your MySQL credentials. Replace your-username and your-password with your MySQL credentials.

```
spring.datasource.url=jdbc:mysql://localhost:3306/scorestore
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```
7. Launch VS Code.
8. Click on File > Open Folder and select the cloned project folder.
9. Search and Install the following extensions:
    - Java Extension Pack (includes Java support, Maven, and debugging tools).
    - Spring Boot Extension Pack (optional, for Spring Boot-specific tools).

10. Open the integrated terminal in VS Code.
11. Navigate the terminal to project directory if not already there yet.
12.  Run the following Maven command to build the project:
    - mvn clean install
13. After building the project, run the application using the following command:
    - mvn spring-boot:run
   
14. Open a web browser and navigate to:
    - http://localhost:8080
    - To run unit tests, use the below command:
      - _mvn test_





