package br.com.alverad.ecommerce.application.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth")
public record AuthProperties(String clientDefault) {

}
