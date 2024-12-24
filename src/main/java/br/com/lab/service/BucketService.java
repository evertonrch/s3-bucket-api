package br.com.lab.service;

import br.com.lab.dto.BucketResponse;
import br.com.lab.exception.BucketNaoVazioException;
import br.com.lab.exception.BucketNotFoundException;
import br.com.lab.exception.RecursoNaoAcessivelException;
import br.com.lab.factory.S3ClientFactory;
import br.com.lab.rule.BucketJaExisteRule;
import br.com.lab.rule.ValidaRegiaoRule;
import br.com.lab.utils.DataUtils;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BucketService {

    private static final Logger log = LoggerFactory.getLogger(BucketService.class);

    private final S3ClientFactory s3Client;
    private final BucketJaExisteRule bucketJaExisteRule;
    private final ValidaRegiaoRule validaRegiaoRule;

    public BucketService(S3ClientFactory s3Client, BucketJaExisteRule bucketJaExisteRule, ValidaRegiaoRule validaRegiaoRule) {
        this.s3Client = s3Client;
        this.bucketJaExisteRule = bucketJaExisteRule;
        this.validaRegiaoRule = validaRegiaoRule;
    }

    public BucketResponse createBucket(String bucketName, String regionName) {
        Regions regiao = validaRegiaoRule.validar(regionName);

        try {
            AmazonS3 client = s3Client.createClient(regiao);

            bucketJaExisteRule.validar(bucketName, client);

            Bucket bucket = client.createBucket(bucketName);
            bucket.setCreationDate(new Date());
            var toLocalDateTime = DataUtils.toLocalDateTime(bucket.getCreationDate());

            return new BucketResponse(bucket.getName(), client.getRegionName(), toLocalDateTime);
        } catch (AmazonS3Exception ex) {
            if (ex.getErrorType().equals(AmazonServiceException.ErrorType.Client)) {
                throw new RecursoNaoAcessivelException("Usuário não tem permissão para essa ação.");
            }
            log.error("Algo deu errado. {}, type: {}", ex.getMessage(), ex.getErrorType());
            throw ex;
        }
    }

    public List<BucketResponse> listBuckets() {
        AmazonS3 client = s3Client.createClient(null);
        var buckets = Lists.transform(client.listBuckets(), bucket -> {
            var toLocalDate = DataUtils.toLocalDateTime(bucket.getCreationDate());
            String bucketName = client.getBucketLocation(bucket.getName());

            return new BucketResponse(bucket.getName(), bucketName, toLocalDate);
        });

        return buckets;
    }

    public void deleteBucket(String bucketName) {
        AmazonS3 client = s3Client.createClient(null);
        try {
            AmazonS3 clientWithRegion = getClientWithRegion(bucketName, client);

            clientWithRegion.deleteBucket(new DeleteBucketRequest(bucketName));
            log.info("Bucket {} na região {} deletado.", bucketName, clientWithRegion.getRegion());
        } catch (AmazonS3Exception ex) {
            log.error("Bucket '{}' não encontrado", bucketName);
            throw new BucketNotFoundException("Bucket não existe", ex);
        }
    }

    public void deleteAllBuckets() {
        AmazonS3 client = s3Client.createClient(null);
        client.listBuckets().forEach(bucket -> {
            AmazonS3 clientWithRegion = getClientWithRegion(bucket.getName(), client);

            var objects = clientWithRegion.listObjectsV2(bucket.getName());
            if(objects.getKeyCount() > 0) {
                log.error("Erro ao deletar o bucket '{}'", bucket.getName());
                throw new BucketNaoVazioException("O Bucket precisa está vazio.");
            }

            clientWithRegion.deleteBucket(new DeleteBucketRequest(bucket.getName()));
            log.info("Bucket {} na região {} deletado.", bucket.getName(), clientWithRegion.getRegion());
        });
    }

    private AmazonS3 getClientWithRegion(String bucketName, AmazonS3 client) {
        String region = client.getBucketLocation(bucketName);
        if ("US".equals(region)) {
            region = "us-east-1";
        }

        return s3Client.createClient(Regions.fromName(region));
    }
}
