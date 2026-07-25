package com.gabryel.notificationservice.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.Connection;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfiguration {

    @Value("web-client.connect-timeout-ms")
    private int connectTimeoutMs;

    @Value("web-client.read-write-timeout-sec")
    private int readWriteTimeoutSec;

    @Value("web-client.response-timeout-sec")
    private int responseTimeoutSec;

    @Bean
    public WebClient genericWebClient() {
        return WebClient.builder()
                .clientConnector(createClientHttpConnector())
                .build();
    }

    private ClientHttpConnector createClientHttpConnector() {
        return new ReactorClientHttpConnector(buildHttpClient());
    }

    private HttpClient buildHttpClient() {
        return HttpClient.create(buildConnectionProvider())
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeoutMs)
                .responseTimeout(Duration.ofSeconds(responseTimeoutSec))
                .doOnConnected(this::configureTimeouts);
    }

    private ConnectionProvider buildConnectionProvider() {
        return ConnectionProvider.builder("custom-connection-pool")
                .maxConnections(500)
                .maxIdleTime(Duration.ofSeconds(20))
                .maxLifeTime(Duration.ofMinutes(5))
                .pendingAcquireTimeout(Duration.ofSeconds(5))
                .evictInBackground(Duration.ofSeconds(30))
                .build();
    }

    private void configureTimeouts(Connection connection) {
        connection.addHandlerLast(new ReadTimeoutHandler(readWriteTimeoutSec, TimeUnit.SECONDS))
                .addHandlerLast(new WriteTimeoutHandler(readWriteTimeoutSec, TimeUnit.SECONDS));
    }

}
