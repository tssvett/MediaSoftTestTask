package com.warehousesystem.app.businesslogic.integration.account.impl;

import com.warehousesystem.app.businesslogic.integration.account.AccountServiceClient;
import com.warehousesystem.app.properties.AccountProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceClientImpl implements AccountServiceClient {

    private final AccountProperties accountProperties;
    private final WebClient accountWebClient;

    @Override
    public CompletableFuture<Map<String, String>> getAccountsByLogins(List<String> logins) {
        return accountWebClient
                .post()
                .uri(accountProperties.getMethods().getGetAccount())
                .bodyValue(logins)
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Map<String, String>>() {
                })
                .onErrorResume(err -> Flux.empty())
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(3)))
                .singleOrEmpty().toFuture();
    }
}
