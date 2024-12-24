package br.com.lab.controller;

import br.com.lab.dto.BucketRequest;
import br.com.lab.dto.BucketResponse;
import br.com.lab.service.BucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buckets")
public class BucketController {

    private final BucketService bucketService;

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @PostMapping()
    public ResponseEntity<BucketResponse> createBucket(@RequestBody BucketRequest bucketRequest) {
        var bucketResponse = bucketService.createBucket(bucketRequest.bucketName(), bucketRequest.region());
        return new ResponseEntity<>(bucketResponse, HttpStatus.CREATED);
    }
}
