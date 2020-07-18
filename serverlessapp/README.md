# QuarkusPlaybook

## Building and Running Lambda Functions

Maven Building
```
mvn archetype:generate -DarchetypeGroupId=io.quarkus -DarchetypeArtifactId=quarkus-amazon-lambda-archetype -DarchetypeVersion=1.6.0.Final
cd serverlessapp/
mvn clean package -DskipTests
```

Configure aws cli if not done (use ap-south-1 for region)
```
aws configure --profile oprexeq
```

Function can be created using
```
LAMBDA_ROLE_ARN="arn:aws:iam::221252253450:role/lambda-role" sh target/manage.sh create
```

