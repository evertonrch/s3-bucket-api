package br.com.lab.exception;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestException {

    @ExceptionHandler(BucketExistenteException.class)
    public ProblemDetail handleBucketExistenteException(BucketExistenteException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(RecursoNaoAcessivelException.class)
    public ProblemDetail handleRecursoNaoAcessivelException(RecursoNaoAcessivelException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(RegiaoInvalidaException.class)
    public ProblemDetail handleRegiaoInvalidaException(RegiaoInvalidaException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(BucketNotFoundException.class)
    public ProblemDetail handleBucketNotFoundException(BucketNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({AmazonS3Exception.class, SdkClientException.class})
    public ProblemDetail handleAmazonS3Exception() {
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno.");
    }

    @ExceptionHandler(BucketNaoVazioException.class)
    public ProblemDetail handleBucketNaoVazioException(BucketNaoVazioException ex) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        detail.setTitle(HttpStatus.CONFLICT.getReasonPhrase());
        detail.setDetail(ex.getMessage());
        return detail;
    }
}
