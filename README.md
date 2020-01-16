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

 - make sure the test API is running on http://0.0.0.0:8080
 - sbt run
 - curl http://0.0.0.0:1337/50

or 

##### method 2

 - build a local docker image using ./makeDocker.sh
 - replace <YOUR IMAGE HERE> line with your image, and execute 'docker-compose up'
 - curl http://0.0.0.0:1337/50 or use ./curlLoop <# of requests>

 


