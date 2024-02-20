# Demo Stop The Crazy Train - Lego Train monitoring app

![lego](https://www.lego.com/cdn/cs/set/assets/blt95604d8cc65e26c4/CITYtrain_Hero-XL-Desktop.png?fit=crop&format=webply&quality=80&width=1600&height=1000&dpr=1)

## Description

This application is a part of global demo "Stop The Crazy train" :
“ The train is running mad at full speed and has no driver ! Your mission, should you choose to accept it, is to train and deploy an AI model at the edge to stop the train before it crashes. This message will self-destruct in five seconds. Four. three. Two. one.  tam tam tada tum tum tada tum tum tada tum tum tada tiduduuuuummmm tiduduuuuuuuuummm ”


## Objectives

Showcase a Quarkus application that monitor the lego train actions, the application is triggered when a cloud event is produced in the topic `train-monitoring`. The application will extract the intial image captured from the camera, add the labels and the squares calculated by the AI application and construc a html page that allow the monitoring of the train.  



### Prerequisites
 
You will need:
  - podman /docker
  - Java openjdk version "17.0.9" 
  - Quarkus
  - Maven

  - Kafka broker (we use AMQ Streams)


### Dev run
run AMQ Streams 
```sh
docker-compose up
```
run the producer
```sh
cd producer
mvn quarkus:dev -DskipTests=true   
```

run the consumer
```sh
cd producer
mvn quarkus:dev -DskipTests=true   
```

Go to http://localhost:8083 and monitor the train


