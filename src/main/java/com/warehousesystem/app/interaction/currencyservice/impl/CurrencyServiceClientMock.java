package com.warehousesystem.app.interaction.currencyservice.impl;

import com.warehousesystem.app.currency.CurrencyRate;
import com.warehousesystem.app.interaction.currencyservice.CurrencyServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Primary
@Service
@Slf4j
@ConditionalOnProperty(name = "rest.currency-service.mock.enabled", havingValue = "true", matchIfMissing = false)
public class CurrencyServiceClientMock implements CurrencyServiceClient {

    @Override
    public CurrencyRate getCurrencyRate() {
        Random random = new Random();
        BigDecimal exchangeRateCNY = BigDecimal.valueOf(1 + 200 * random.nextDouble());
        BigDecimal exchangeRateUSD = BigDecimal.valueOf(1 + 500 * random.nextDouble());
        BigDecimal exchangeRateEUR = BigDecimal.valueOf(1 + 600 * random.nextDouble());
        log.info("Get exchange rate from mock");

        return new CurrencyRate(exchangeRateCNY, exchangeRateUSD, exchangeRateEUR);
    }
}
