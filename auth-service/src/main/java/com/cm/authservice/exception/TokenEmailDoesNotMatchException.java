package com.cm.authservice.exception;

public class TokenEmailDoesNotMatchException extends RuntimeException {
    public TokenEmailDoesNotMatchException(String message) {super(message);}
}
