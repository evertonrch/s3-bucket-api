package br.com.lab.exception;

public class RegiaoInvalidaException extends RuntimeException{
    public RegiaoInvalidaException(String message) {
        super(message);
    }

    public RegiaoInvalidaException(String message, Throwable cause) {
        super(message, cause);
    }
}
