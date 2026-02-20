package com.funfair.api.common;

import org.springframework.beans.factory.annotation.Value;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.net.URI;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.regions.Region;

@Configuration
public class DigitalOceanSpacesConfig {
	
	 @Value("${do.spaces.access-key}")
	 private String accessKey;

	 @Value("${do.spaces.secret-key}")
	 private String secretKey;

	 @Value("${do.spaces.region}")
	 private String region;

	 @Value("${do.spaces.endpoint}")
	 private String endpoint;

	 @Bean
	 public S3Client s3Client() {

	  AwsBasicCredentials credentials =
	    AwsBasicCredentials.create(accessKey, secretKey);

	  return S3Client.builder()
	    .endpointOverride(URI.create(endpoint))
	    .region(Region.of(region))
	    .credentialsProvider(
	      StaticCredentialsProvider.create(credentials))
	     .forcePathStyle(true)
	    .build();
	 }

}
