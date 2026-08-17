package br.com.inova.sigin.integracao.core;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sigin.core")
public record CoreClientProperties(
        String url
) {
}