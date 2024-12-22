package br.com.lab.service;

import br.com.lab.dto.BucketResponse;
import br.com.lab.exception.RecursoNaoAcessivelException;
import br.com.lab.rule.BucketJaExisteRule;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BucketService {

    private static final Logger log = LoggerFactory.getLogger(BucketService.class);

    private final AmazonS3 s3Client;
    private final BucketJaExisteRule bucketJaExisteRule;

    public BucketService(AmazonS3 s3Client, BucketJaExisteRule bucketJaExisteRule) {
        this.s3Client = s3Client;
        this.bucketJaExisteRule = bucketJaExisteRule;
    }

    public BucketResponse createBucket(String bucketName) {
        bucketJaExisteRule.validar(bucketName);

        try {
            Bucket bucket = s3Client.createBucket(bucketName);
            bucket.setCreationDate(new Date());

            return new BucketResponse(bucket);
        } catch (AmazonS3Exception ex) {
            if (ex.getErrorType().equals(AmazonServiceException.ErrorType.Client)) {
                throw new RecursoNaoAcessivelException("Usuário não tem permissão para essa ação.");
            }
            log.error("Algo deu errado. {}, type: {}", ex.getMessage(), ex.getErrorType());
            throw ex;
        }
    }
}
