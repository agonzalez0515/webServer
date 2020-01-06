# Web Server  [![Build Status](https://travis-ci.org/agonzalez0515/webServer.svg?branch=master)](https://travis-ci.org/agonzalez0515/webServer)



This project uses:
* Gradle 6
* Java 13
* Junit 4
* Mockito
* Deployed to Heroku

https://secret-wave-52844.herokuapp.com/

### Run Server Locally

* On port 5000: `gradle run`

* On custom port: `gradle run -Dport=yourPortNumber`


### Tests

* Run unit tests `gradle test`
* Run integration tests from root `cd gisrc/integration-test/http_server_spec/ && bundle exec spinach`
