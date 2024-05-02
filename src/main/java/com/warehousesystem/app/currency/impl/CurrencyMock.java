package com.warehousesystem.app.currency.impl;

import com.warehousesystem.app.currency.CurrencyRate;
import com.warehousesystem.app.currency.CurrencyServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@ConditionalOnProperty(name = "currency-service.mock", havingValue = "true")
@Service
@Slf4j
public class CurrencyMock implements CurrencyServiceClient {

    @Override
    public CurrencyRate getCurrencyRate() {
        Random random = new Random();
        BigDecimal exchangeRateCNY = BigDecimal.valueOf(1 + 200 * random.nextDouble());
        BigDecimal exchangeRateUSD = BigDecimal.valueOf(1 + 500 *random.nextDouble());
        BigDecimal exchangeRateEUR = BigDecimal.valueOf(1 + 600 * random.nextDouble());
        log.info("Get exchange rate from mock");
        return new CurrencyRate(exchangeRateCNY, exchangeRateUSD, exchangeRateEUR);
    }

}
