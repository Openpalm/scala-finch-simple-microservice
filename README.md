# scala-finch micro service

a microservice using 
  - finch as a server
  - caffeine as a cache

config via 
  - src/main/scala/Globals.scala
  - docker-compose env var

### requirements

- java 1.8
- sbt

#### to run

- clone this repo 

##### method 1

 - make sure the test API uis running on http://0.0.0.0:8080
 - sbt run
 - curl http://0.0.0.0:1337/50

or 

##### method 2

 - build a local docker image using ./makeDocker.sh
 - docker-compose up
 - curl http://0.0.0.0:1337/50


