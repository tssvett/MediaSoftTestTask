package com.warehousesystem.app.currency;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.warehousesystem.app.enums.CurrencyType;
import com.warehousesystem.app.interaction.currencyservice.CurrencyServiceClient;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.math.BigDecimal;
import java.util.Optional;

@Getter
@Setter
@Component
@Slf4j
@RequiredArgsConstructor
public class CurrencyRateProvider {
    private final CurrencyServiceClient currencyServiceClient;
    private CurrencyType currencyType;

    public BigDecimal getCurrencyValue() {
        return Optional.ofNullable(getCurrencyValueFromService()).orElseGet(this::getCurrencyValueFromFile);
    }

    private @Nullable BigDecimal getCurrencyValueFromService() {
        return Optional.ofNullable(currencyServiceClient.getCurrencyRate()).map(this::getCurrencyValueFromCurrencyType).orElse(null);
    }

    private BigDecimal getCurrencyValueFromFile() {
        CurrencyRate currencyRate = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            currencyRate = objectMapper.readValue(new File("src/main/resources/exchange-rate.json"), CurrencyRate.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("Get exchange rate from file");

        return getCurrencyValueFromCurrencyType(currencyRate);
    }

    private BigDecimal getCurrencyValueFromCurrencyType(CurrencyRate currencyRate) {
        return switch (currencyType) {
            case RUB -> BigDecimal.ONE;
            case USD -> currencyRate.getExchangeRateUSD();
            case CNY -> currencyRate.getExchangeRateCNY();
            case EUR -> currencyRate.getExchangeRateEUR();
        };
    }
}
