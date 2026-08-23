package br.com.inova.sigin.integracao.core;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(CoreClientProperties.class)
public class CoreClientConfig {

    @Bean
    RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    RestClient coreRestClient(CoreClientProperties properties) {
        return RestClient.builder()
                .baseUrl(properties.url())
                .build();
    }
}