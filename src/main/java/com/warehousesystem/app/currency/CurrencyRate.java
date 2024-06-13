package com.warehousesystem.app.currency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRate {

    private BigDecimal exchangeRateCNY;

    private BigDecimal exchangeRateUSD;

    private BigDecimal exchangeRateEUR;
}
