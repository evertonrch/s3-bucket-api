package br.com.lab.rule;

import br.com.lab.exception.BucketExistenteException;
import com.amazonaws.services.s3.AmazonS3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BucketJaExisteRule {

    private static final Logger log = LoggerFactory.getLogger(BucketJaExisteRule.class);

    private final AmazonS3 s3Client;

    public BucketJaExisteRule(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public void validar(String bucketName) {
        if (s3Client.doesBucketExistV2(bucketName)) {
            log.error("erro ao criar o bucket {}", bucketName);
            throw new BucketExistenteException(String.format("Bucket %s já existe.", bucketName));
        }
    }
}
