package com.warehousesystem.app.interaction.currencyservice.impl;

import com.warehousesystem.app.currency.CurrencyRate;
import com.warehousesystem.app.interaction.currencyservice.CurrencyServiceClient;
import com.warehousesystem.app.properties.RestProperties;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyServiceClientImpl implements CurrencyServiceClient {
    private final WebClient webClient;
    private final RestProperties restProperties;

    @Cacheable(value = "currency-cache", unless = "#result == null")
    @Override
    public @Nullable CurrencyRate getCurrencyRate() {
        try {
            //Функция выполняет retry 3 раза
            return webClient
                    .get()
                    .uri(restProperties.getMethods().getGetCurrency())
                    .retrieve()
                    .bodyToMono(CurrencyRate.class)
                    .retryWhen(Retry.fixedDelay(2, Duration.ofMillis(300)))
                    .doOnError(error -> log.error("Error with currency service: " + error.getMessage()))
                    .block();
        } catch (Exception e) {
            log.error("Error with currency service: " + e.getMessage());

            return null;
        }
    }
}
