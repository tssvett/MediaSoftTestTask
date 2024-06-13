package com.warehousesystem.app.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "currency-service")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestProperties {

    private String host;
    private String mock;
    private Methods methods;


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Methods {
        private String getCurrency;
    }
}
