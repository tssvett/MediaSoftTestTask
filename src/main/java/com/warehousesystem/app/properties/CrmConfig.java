package com.warehousesystem.app.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "crm-service")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CrmConfig {

    private String host;
    private Methods methods;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Methods {
        private String getInn;
    }
}
