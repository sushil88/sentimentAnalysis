package com.serverless;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import org.apache.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.comprehend.model.DetectSentimentRequest;
import com.amazonaws.services.comprehend.model.DetectSentimentResult;


public class Handler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private static final Logger LOG = Logger.getLogger(Handler.class);

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
		String comment = ((Map<String, String>) input.get("queryStringParameters")).get("comment");
		LOG.info("received: " + comment);

		Map<String, String> headers = new HashMap<>();

		AWSCredentialsProvider awsCreds = DefaultAWSCredentialsProviderChain.getInstance();

		AmazonComprehend comprehendClient =
				AmazonComprehendClientBuilder.standard()
						.withCredentials(awsCreds)
						.withRegion("us-east-1")
						.build();

		// Call detectSentiment API
		LOG.info("Calling DetectSentiment");
		DetectSentimentRequest detectSentimentRequest = new DetectSentimentRequest().withText(comment)
				.withLanguageCode("en");
		DetectSentimentResult detectSentimentResult = comprehendClient.detectSentiment(detectSentimentRequest);
		LOG.info(detectSentimentResult);

//		Response responseBody = new Response(detectSentimentResult.toString());

		headers.put("X-Powered-By", "AWS Lambda & Serverless");
		headers.put("Content-Type", "application/json");
		headers.put("Access-Control-Allow-Origin", "*");
		return ApiGatewayResponse.builder()
				.setStatusCode(200)
				.setObjectBody(detectSentimentResult.toString())
				.setHeaders(headers)
				.build();
	}
}
