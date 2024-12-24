package br.com.lab.exception;

public class BucketNotFoundException extends RuntimeException {
    public BucketNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
