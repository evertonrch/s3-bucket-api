package br.com.lab.dto;

import br.com.lab.utils.DataUtils;
import com.amazonaws.services.s3.model.Bucket;

import java.time.LocalDateTime;

public record BucketResponse(String bucketName, String region,  LocalDateTime dataHoraCriacao) {}
