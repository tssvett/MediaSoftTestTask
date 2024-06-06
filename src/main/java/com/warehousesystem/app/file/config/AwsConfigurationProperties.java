package com.warehousesystem.app.file.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "aws")
@Component
@Getter
@Setter
public class AwsConfigurationProperties {
    private String path;
    private String accessKeyId;
    private String secretAccessKey;
    private String region;
    private String bucket;
}
