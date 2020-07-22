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
  1. frontend 
  `yarn install`
  `yarn start`
  2. apiserver and playbookserver
  `mvn install`
  `mvn quarkus:dev`
6. Point your browser to `http://localhost:3000`
