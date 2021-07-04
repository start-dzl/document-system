package com.meiya.uploader.documentsystem.core.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * 全局配置
 *
 * @author yu
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "global")
public class GlobalProperties {

    private String secret;

    private int expire;

    private Boolean mock;
}
