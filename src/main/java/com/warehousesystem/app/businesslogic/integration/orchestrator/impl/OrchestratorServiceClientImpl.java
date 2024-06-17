package com.warehousesystem.app.businesslogic.integration.orchestrator.impl;

import com.warehousesystem.app.businesslogic.integration.orchestrator.OrchestratorServiceClient;
import com.warehousesystem.app.dto.orchestrator.OrchestratorRequestDto;
import com.warehousesystem.app.properties.OrchestratorProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrchestratorServiceClientImpl implements OrchestratorServiceClient {
    private final OrchestratorProperties orchestratorProperties;
    private final WebClient orchestratorWebClient;

    @Override
    public UUID startOrderConfirmProcess(OrchestratorRequestDto orchestratorRequestDto) throws ExecutionException, InterruptedException {
        log.info("Form data: {}", orchestratorRequestDto);
        return orchestratorWebClient
                .post()
                .uri(orchestratorProperties.getMethods().getProcessStart())
                .body(BodyInserters.fromValue(orchestratorRequestDto))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<UUID>() {
                })
                .onErrorResume(err -> Mono.empty())
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(3)))
                .toFuture().get();
    }
}
