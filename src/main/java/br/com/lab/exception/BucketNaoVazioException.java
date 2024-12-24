package br.com.lab.exception;

public class BucketNaoVazioException extends RuntimeException{
    public BucketNaoVazioException(String message) {
        super(message);
    }
}
