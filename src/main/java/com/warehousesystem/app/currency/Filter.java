package com.warehousesystem.app.currency;

import com.warehousesystem.app.enums.CurrencyType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Getter
@Setter
@Component
@RequiredArgsConstructor
public class Filter extends OncePerRequestFilter {
    private final CurrencyRateProvider currencyRateProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String currencyString = request.getHeader("Currency");
        try {
            Optional.ofNullable(currencyString)
                    .map(CurrencyType::valueOf)
                    .ifPresent(currencyRateProvider::setCurrencyType);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}