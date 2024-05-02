package com.warehousesystem.app.currency;

import com.warehousesystem.app.enums.CurrencyType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Filter extends OncePerRequestFilter {

    @Autowired
    private CurrencyRateProvider currencyRateProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String currencyString = request.getHeader("Currency");
        try {
            currencyRateProvider.setCurrencyType(CurrencyType.valueOf(currencyString));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if (currencyRateProvider.getCurrencyType() == null) {
            currencyRateProvider.setCurrencyType(CurrencyType.RUB);
        }
        filterChain.doFilter(request, response);
    }
}