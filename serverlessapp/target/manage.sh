#!/bin/sh

function cmd_create() {
  echo Creating function
  set -x
  aws lambda create-function \
    --profile oprexeq \
    --function-name ${FUNCTION_NAME} \
    --zip-file ${ZIP_FILE} \
    --handler ${HANDLER} \
    --runtime ${RUNTIME} \
    --role ${LAMBDA_ROLE_ARN} \
    --timeout 15 \
    --memory-size 128 \
    ${LAMBDA_META}
# Enable and move this param above ${LAMBDA_META}, if using AWS X-Ray
#    --tracing-config Mode=Active \
}

function cmd_delete() {
  echo Deleting function
  set -x
  aws lambda delete-function --profile oprexeq --function-name ${FUNCTION_NAME}
}

function cmd_invoke() {
  echo Invoking function
  set -x
  aws lambda invoke file://target/response.txt \
    --profile oprexeq \
    --function-name ${FUNCTION_NAME} \
    --payload file://target/payload.json \
    --log-type Tail \
    --query 'LogResult' \
    --output text |  base64 --decode
  { set +x; } 2>/dev/null
  cat response.txt && rm -f response.txt
}

function cmd_update() {
  echo Updating function
  set -x
  aws lambda update-function-code \
    --profile oprexeq \
    --function-name ${FUNCTION_NAME} \
    --zip-file ${ZIP_FILE}
}

FUNCTION_NAME=Serverlessapp
HANDLER=io.quarkus.amazon.lambda.runtime.QuarkusStreamHandler::handleRequest
RUNTIME=java11
ZIP_FILE=fileb:///Users/johne/Documents/CodeRepository/QuarkusHackathonWorkspace/serverlessapp/target/function.zip

function usage() {
  [ "_$1" == "_" ] && echo "\nUsage (JVM): \n$0 [create|delete|invoke]\ne.g.: $0 invoke"
  [ "_$1" == "_" ] && echo "\nUsage (Native): \n$0 native [create|delete|invoke]\ne.g.: $0 native invoke"

  [ "_" == "_`which aws 2>/dev/null`" ] && echo "\naws CLI not installed. Please see https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-install.html"
  [ ! -e $HOME/.aws/credentials ] && [ "_$AWS_ACCESS_KEY_ID" == "_" ] && echo "\naws configure not setup.  Please execute: aws configure"
  [ "_$LAMBDA_ROLE_ARN" == "_" ] && echo "\nEnvironment variable must be set: LAMBDA_ROLE_ARN\ne.g.: export LAMBDA_ROLE_ARN=arn:aws:iam::123456789012:role/my-example-role"
}

if [ "_$1" == "_" ] || [ "$1" == "help" ]
 then
  usage
fi

if [ "$1" == "native" ]
then
  RUNTIME=provided
  ZIP_FILE=fileb:///Users/johne/Documents/CodeRepository/QuarkusHackathonWorkspace/serverlessapp/target/function.zip
  FUNCTION_NAME=ServerlessappNative
  LAMBDA_META="--environment Variables={DISABLE_SIGNAL_HANDLERS=true}"
  shift
fi

while [ "$1" ]
do
  eval cmd_${1}
  { set +x; } 2>/dev/null
  shift
done

