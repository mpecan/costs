[![Build Status](https://travis-ci.org/mpecan/costs.svg?branch=master)](https://travis-ci.org/mpecan/costs)

# Costs explorer application

## Prerequisites
This project requires a postgres database server to be running. 

For ease of use and setup you can use the supplied docker-compose file by running `docker-compose up -d db`.

## Building
*Backend* - Simply run `./gradlew build` and the application will be tested and built (it requires access to the database for this). The Frontend will be built automatically in this case.

## Running
You can either use the supplied docker-compose and run `docker-compose up` after the project has been built or run `./gradlew bootRun` (which will also build the client).

## Testing
*prerequisite for backend* - Run the test database by running `docker-compose up -d test-db`

*Backend* - Running `./gradlew test` will run all application tests including frontend tests. 


*Frontend* - Run `cd client && yarn test` if you want to only build the frontend.