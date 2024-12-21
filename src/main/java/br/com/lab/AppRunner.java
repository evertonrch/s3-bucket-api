package br.com.lab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AppRunner.class);

    @Autowired Environment env;

    @Override
    public void run(String... args) throws Exception {
        log.info("access key {}", env.getProperty("ACCESS_KEY"));
        log.info("secret key {}", env.getProperty("SECRET_KEY"));
        log.info("region name {}", env.getProperty("REGION"));
    }
}