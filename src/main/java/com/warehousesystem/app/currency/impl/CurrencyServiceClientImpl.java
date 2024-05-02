package com.warehousesystem.app.currency.impl;

import com.warehousesystem.app.currency.CurrencyRate;
import com.warehousesystem.app.currency.CurrencyServiceClient;
import com.warehousesystem.app.properties.CurrencyConfig;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;

@Primary
@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class CurrencyServiceClientImpl implements CurrencyServiceClient {

    @Autowired
    private WebClient webClient;

    @Autowired
    private CurrencyConfig currencyConfig;

    @Cacheable(value = "currency-cache", unless = "#result == null")
    @Override
    public @Nullable CurrencyRate getCurrencyRate() {
        try {
            //Функция выполняет retry 3 раза
            return webClient
                    .get()
                    .uri(currencyConfig.getHost() + currencyConfig.getMethods().getGetCurrency())
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
