package com.sid.gl.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.sid.gl")
@ComponentScan(basePackages = {"com.sid.gl"})
public class Config extends AbstractElasticsearchConfiguration {
    @Value("${elasticsearch.url}")
    private String elasticUrl;

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration=
           ClientConfiguration.builder()
                   .connectedTo(elasticUrl)
                   .build();
        return RestClients.create(clientConfiguration).rest();
    }
}
