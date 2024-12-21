package br.com.lab;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class S3BucketApiApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(S3BucketApiApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
