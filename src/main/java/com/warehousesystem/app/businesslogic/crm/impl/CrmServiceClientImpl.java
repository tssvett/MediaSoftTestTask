package com.warehousesystem.app.businesslogic.crm.impl;


import com.warehousesystem.app.businesslogic.crm.CrmServiceClient;
import com.warehousesystem.app.properties.CrmConfig;
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
public class CrmServiceClientImpl implements CrmServiceClient {

    private final CrmConfig crmConfig;
    private final WebClient webClient;

    @Override
    public CompletableFuture<Map<String, String>> getInnsByLogins(List<String> logins) {
        return webClient
                .post()
                .uri(crmConfig.getMethods().getGetInn())
                .bodyValue(logins)
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Map<String, String>>() {
                })
                .onErrorResume(err -> Flux.empty())
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(3)))
                .singleOrEmpty().toFuture();
    }
}


