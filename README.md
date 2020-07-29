# QuarkusPlaybook

## There are multiple projects within this ecosystem


- frontend: React.js application
- common: common beans package
- apiserver: server built on quarkus that exposes api
- playbookserver: server built on quarkus
- serverlessapp: quarkus lambda application

## Prerequisites

1. Mongo DB
2. Apache Kafka
3. AWS account and AWS cli installed with a profile created as `oprexeq`. If a profile of a different name is to be created then change appropriate values in aws_tool.sh

## Steps to run

1. Checkout the repo
2. Verify AWS CLI configuration
3. Start Mongo server
4. Start Kafka server (Zookeeper and Kafka broker)
5. Build projects
  0. common
    `mvn install`
  1. frontend 
  `yarn install`
  `yarn start`
  2. apiserver and playbookserver
  `mvn install`
  `mvn quarkus:dev`
6. Point your browser to `http://localhost:3000`

## Web Sequence Diagram

```
title QuarkusLamdba

UI->WebServer:Submits {filename, code}
WebServer->Kafka: Push {filename, code}
WebServer->Mongo: Save {filename, code}
note right of PlaybookServer: Listener
Kafka->PlaybookServer: Pull {filename, code}
PlaybookServer->PlaybookServer: Start Code Replace
PlaybookServer->PlaybookServer: Start MVN Build
PlaybookServer->AWSCli: Function Create
AWSCli->AWS Lambda: Function Create
PlaybookServer->AWSCli: Function Invoke
AWSCli->AWS Lambda: Function Invoke
PlaybookServer->Kafka: Response from above calls
note right of WebServer: Listener
Kafka->WebServer: Pull Response
```

Try this out in https://www.websequencediagrams.com/
