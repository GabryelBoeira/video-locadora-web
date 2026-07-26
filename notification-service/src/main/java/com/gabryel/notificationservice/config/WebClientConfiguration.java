package com.gabryel.notificationservice.config;

import com.gabryel.notificationservice.config.properties.WebClientProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties(WebClientProperties.class)
public class WebClientConfiguration {

    private final WebClientProperties props;

    public WebClientConfiguration(WebClientProperties props) {
        this.props = props;
    }

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
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, props.connectTimeoutMs())
                .responseTimeout(Duration.ofSeconds(props.responseTimeoutSec()))
                .doOnConnected(this::configureTimeouts);
    }

    private ConnectionProvider buildConnectionProvider() {
        return ConnectionProvider.builder("custom-connection-pool")
                .maxConnections(props.pool().maxConnections())
                .maxIdleTime(Duration.ofSeconds(props.pool().maxIdleTimeSec()))
                .maxLifeTime(Duration.ofMinutes(props.pool().maxLifeTimeMin()))
                .pendingAcquireTimeout(Duration.ofSeconds(props.pool().pendingAcquireTimeoutSec()))
                .evictInBackground(Duration.ofSeconds(props.pool().evictInBackgroundSec()))
                .build();
    }

    private void configureTimeouts(Connection connection) {
        connection
                .addHandlerLast(new ReadTimeoutHandler(props.readWriteTimeoutSec(), TimeUnit.SECONDS))
                .addHandlerLast(new WriteTimeoutHandler(props.readWriteTimeoutSec(), TimeUnit.SECONDS));
    }

}
