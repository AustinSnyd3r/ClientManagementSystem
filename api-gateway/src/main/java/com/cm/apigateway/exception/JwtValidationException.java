package com.cm.apigateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class JwtValidationException {

    @ExceptionHandler(WebClientResponseException.class)
    public Mono<Void> handleUnauthorizedException(ServerWebExchange exchange){
        /*
            Map unauthorized from auth to the correct code. Without this,
            we will return a 500 Internal Server Error.
         */
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }



}
