# Costs explorer application

## Prerequisites
This project requires a postgres database server to be running. 
For ease of use and setup you can use the supplied docker-compose file by running `docker-compose up -d db`.

## Building
Simply run `./gradlew build` and the application will be tested and built (it requires access to the database for this).

## Running
You can either use the supplied docker-compose and run `docker-compose up` after the project has been built or run `./gradlew bootRun`.