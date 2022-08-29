package com.barclays.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.confluent.ksql.api.client.Client;
import io.confluent.ksql.api.client.ClientOptions;

@Configuration
public class KSQLClientProducer {

    @Bean
    Client ksqlClient() {
        ClientOptions options = ClientOptions.create()
                .setHost("cp-ksql-server")
                .setPort(8088);
        return Client.create(options);
    }
}
