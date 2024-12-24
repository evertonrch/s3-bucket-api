package br.com.lab.factory;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.stereotype.Component;

@Component
public class S3ClientFactory {

    private final AWSStaticCredentialsProvider provider;
    private AmazonS3 client;

    public S3ClientFactory(AWSStaticCredentialsProvider provider) {
        this.provider = provider;
    }

    // O client do S3 é imutável por padrão, foi criado uma factory para ter a flexibilidade
    // de alterar a região
    public AmazonS3 createClient(Regions region) {
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(provider)
                .build();

    }

    public AmazonS3 getClient() {
        return this.client;
    }
}
