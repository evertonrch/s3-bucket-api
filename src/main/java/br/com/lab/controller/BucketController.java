package br.com.lab.controller;

import br.com.lab.dto.BucketRequest;
import br.com.lab.dto.BucketResponse;
import br.com.lab.service.BucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<BucketResponse>> buckets() {
        var buckets = bucketService.listBuckets();
        return buckets.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(buckets);
    }

    @DeleteMapping("/{bucketName}")
    public ResponseEntity<Void> deleteBucket(@PathVariable("bucketName") String bucket) {
        bucketService.deleteBucket(bucket);
        return ResponseEntity.noContent().build();
    }
}
