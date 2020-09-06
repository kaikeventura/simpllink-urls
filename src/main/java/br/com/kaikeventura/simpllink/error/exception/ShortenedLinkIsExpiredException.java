package br.com.kaikeventura.simpllink.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ShortenedLinkIsExpiredException extends RuntimeException {
    public ShortenedLinkIsExpiredException(String message) {
        super(message);
    }
}