package br.com.lab.factory;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.stereotype.Component;

import java.util.Objects;


/**
 * Por padrão o client do S3 é imutável, dessa forma foi necessário criar
 * uma factory.
 */

@Component
public class S3ClientFactory {

    private final AWSStaticCredentialsProvider provider;

    public S3ClientFactory(AWSStaticCredentialsProvider provider) {
        this.provider = provider;
    }

    /**
     * Cria um cliente S3 com base na região especificada.
     *
     * @param region Região desejada. Caso seja nulo, será usada a região padrão.
     * @return Instância de AmazonS3.
     */
    public AmazonS3 createClient(Regions region) {
        Regions regiaoEfetiva = (region != null) ? region : Regions.DEFAULT_REGION;

        return AmazonS3ClientBuilder.standard()
                .withRegion(regiaoEfetiva)
                .withCredentials(provider)
                .build();
    }
}
