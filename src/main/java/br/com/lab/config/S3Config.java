package br.com.lab.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${aws.credentials.access-key}")
    private String accessKey;

    @Value("${aws.credentials.secret-key}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @Bean
    public AWSCredentials credentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    @Bean
    public AWSStaticCredentialsProvider provider(AWSCredentials credentials) {
        return new AWSStaticCredentialsProvider(credentials);
    }

    @Bean
    public AmazonS3 s3Client(AWSStaticCredentialsProvider provider) {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(provider)
                .withRegion(region)
                .build();
    }
}
