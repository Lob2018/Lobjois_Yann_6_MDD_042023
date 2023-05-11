# MDD (Monde de Dév)

</br>

This project was generated with:

> [OpenJDK](https://openjdk.org/projects/jdk/19/) version 19.0.2.7-hotspot

> [Spring Boot](https://spring.io/projects/spring-boot) version 3.0.5

> [Spring Security](https://spring.io/projects/spring-security) version 6.0.2

> [Node](https://nodejs.org/docs/v16.18.0/api/) version 16.18.0

> [NPM](https://docs.npmjs.com/cli/v8/commands/npm?v=true) version 8.19.2

> [Angular](https://github.com/angular/angular/tree/14.3.0) version 14.3.0

</br>

# Back-End (with Spring Tool Suite 4 IDE)

</br>

Git clone (Back-End is in the ./back folder):

> git clone https://github.com/Lob2018/Lobjois_Yann_6_MDD_042023

</br>

Set the user's environment variables (batch file with command prompt)

> Double-click on `MDD_CREATE_ENVIRONMENT_VAR.bat`, to set the variables`*` and note their values`*`

[_Those environment variables are needed to use this API_](#user-environment-variables-details)

(double-click on `MDD_DROP_ENVIRONMENT_VAR.bat` if you want to remove them after)

</br>

MySQL version 8.0.31 (MySQL Community Server - GPL):

> Port: 3306

> Create the username and the password (`*`from noted values)

> Create a new schema named: mdd

> Give rights for this username on the mdd schema

> Execute the queries from the script `./resources/script.sql` for the mdd schema

</br>

Start the API with Spring Tool Suite 4:

> Right-click on folder > Run as > Maven install (install Maven dependencies)

> Boot dashboard > Select the project > Start the process (start the Mdd API)

The ports used

> Tomcat port: 8080

> Angular port: 4200

</br>

Extra resources

> A Postman's collection `./resources/mdd.postman_collection.json`

</br>

## Back-End documentation

</br>

Generate the Back-End project Javadoc

> Inside ./back execute this Maven command: mvn javadoc:javadoc -e

> Open: ./back/target/site/apidocs/index.html

Back-End API documentation

> Start the API

> Swagger UI in HTML is available at `http://localhost:8080/swagger-ui/index.html#/`

</br>

# Front-End

</br>

Inside ./front :

> Install : npm install

> Start : npm run start

</br>

## Front-End documentation

</br>

Generate the Front-End project documentation with Compodoc

> Inside ./front execute this command: npm run docs

> Open: ./front/documentation/index.html

</br>

## User environment variables details

</br>

> Variable for MySQL password: `MDD_YL_API_MYSQL_PASSWORD`

> Variable for MySQL username: `MDD_YL_API_MYSQL_USERNAME`

> Variable for the JWT issuer: `MDD_YL_API_JWTISSUER`

> Variable for the JWT secret: `MDD_YL_API_JWTSECRET`

</br>
