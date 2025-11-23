package com.cm.apigateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class JwtValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private final WebClient webClient;

    public JwtValidationGatewayFilterFactory(WebClient.Builder webClientBuilder,
                                             @Value("${auth.service.url}") String authServiceUrl){
        this.webClient = webClientBuilder.baseUrl(authServiceUrl).build();
    }

    @Override
    public GatewayFilter apply(Object config){
        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            // Check for missing token or invalid formatting.
            if(token == null || !token.startsWith("Bearer ")){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // Now check if the token they supplied was valid using our endpoint of auth service.

            return webClient.get()
                    .uri("/auth/validate")
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .exchangeToMono(clientResponse -> {
                        if(clientResponse.statusCode().is4xxClientError()
                                || clientResponse.statusCode().is5xxServerError()){
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                        }

                        // Grab auth id header.
                        String authId = clientResponse.headers()
                                .asHttpHeaders()
                                .getFirst("X-AUTH-ID");

                        // Make a mutated request to append the headers.
                        ServerHttpRequest mutatedRequest = exchange.getRequest()
                                .mutate()
                                .header("X-AUTH-ID", authId)
                                .build();

                        return chain.filter(exchange.mutate().request(mutatedRequest).build());
                    });
        };
    }
}
