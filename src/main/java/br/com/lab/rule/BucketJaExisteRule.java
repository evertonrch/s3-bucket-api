package br.com.lab.rule;

import br.com.lab.exception.BucketExistenteException;
import com.amazonaws.services.s3.AmazonS3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BucketJaExisteRule {

    private static final Logger log = LoggerFactory.getLogger(BucketJaExisteRule.class);

    public void validar(String bucketName, AmazonS3 client) {
        if (client.doesBucketExistV2(bucketName)) {
            log.error("erro ao criar o bucket {}", bucketName);
            throw new BucketExistenteException(String.format("Bucket %s já existe.", bucketName));
        }
    }
}
