<!--
title: 'AWS Comprehend Simple HTTP Endpoint example in Java'
description: 'This example demonstrates how to setup a simple HTTP GET endpoint using Java. Once you ping it with comment text, it will reply with the sentiment analysis details for given comment.'
layout: Doc
framework: v1
platform: AWS
language: Java
-->
# Simple HTTP Endpoint Example in Java for AWS Comprehend APIs

This example demonstrates how to setup a simple HTTP GET endpoint using Java. Once you ping it with comment text, it will reply with the sentiment analysis details for given comment.

[Jackson](https://github.com/FasterXML/jackson) is used to serialize objects to JSON.


## Use Cases

- Wrapping an existing internal or external endpoint/service

## Build

It is required to build prior to deploying. You can build the deployment artifact using Gradle or Maven.

### Gradle

In order to build using Gradle simply run

```bash
gradle wrapper # to build the gradle wrapper jar
./gradlew build # to build the application jar
```

The expected result should be similar to:

```bash
Starting a Gradle Daemon, 1 incompatible Daemon could not be reused, use --status for details
:compileJava
:processResources
:classes
:jar
:assemble
:buildZip
:compileTestJava UP-TO-DATE
:processTestResources UP-TO-DATE
:testClasses UP-TO-DATE
:test UP-TO-DATE
:check UP-TO-DATE
:build

BUILD SUCCESSFUL

Total time: 8.195 secs
```

### Maven

In order to build using Maven simply run

```bash
mvn package
```

Note: you can install Maven with

1. [sdkman](http://sdkman.io/) using `sdk install maven` (yes, use as default)
2. `sudo apt-get install mvn`
3. `brew install maven`

If you use Maven to build, then in `serverless.yml` you have to replace

```yaml
package:
  artifact: build/distributions/aws-java-simple-http-endpoint.zip
```
with
```yaml
package:
  artifact: target/aws-java-simple-http-endpoint.jar
```
before deploying.

Before deploying you have to provide aws profile. Check details on how to configure AWS profile here : https://docs.aws.amazon.com/cli/latest/userguide/cli-multiple-profiles.html.

In `serverless.yml` you have to replace

```yaml
package:
  profile: <YOUR_AWS_PROFILE>
```
with
```yaml
package:
  artifact: <YOUR_ACTUAL_AWS_PROFILE>
```
before deploying.

## Deploy

After having built the deployment artifact using Gradle or Maven as described above you can deploy by simply running

```bash
serverless deploy
```

The expected result should be similar to:

```bash
Serverless: Creating Stack...
Serverless: Checking Stack create progress...
.....
Serverless: Stack create finished...
Serverless: Uploading CloudFormation file to S3...
Serverless: Uploading service .zip file to S3...
Serverless: Updating Stack...
Serverless: Checking Stack update progress...
..............................
Serverless: Stack update finished...
Service Information
service: aws-java-simple-http-endpoint
stage: dev
region: us-east-1
api keys:
  None
endpoints:
  GET -  https://XXXXXXXX.execute-api.us-east-1.amazonaws.com/dev?comment=success
functions:
  aws-java-simple-http-endpoint-dev-sentimentAnalyzer: arn:aws:lambda:us-east-1:XXXXXXXX:function:sentiment-analysis-dev-sentimentAnalyzer:1

```

## Usage

You can now invoke the Lambda function directly and even see the resulting log via

```bash
serverless invoke --function sentimentAnalyzer --log
```

The expected result should be similar to:

```bash
{
    "statusCode": 200,
    "body": "{Sentiment: POSITIVE,SentimentScore: {Positive: 0.70675105,Negative: 0.014205746,Neutral: 0.2683226,Mixed: 0.010720575}}",
    "headers": {
        "X-Powered-By": "AWS Lambda & Serverless",
        "Content-Type": "application/json"
    },
    "isBase64Encoded": false
}
