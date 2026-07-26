package com.gabryel.notificationservice.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "web-client")
public record WebClientProperties(
        int connectTimeoutMs,
        int readWriteTimeoutSec,
        int responseTimeoutSec,
        Pool pool
) {
    // Record aninhado para organizar as configurações do Connection Pool
    public record Pool(
            int maxConnections,
            int maxIdleTimeSec,
            int maxLifeTimeMin,
            int pendingAcquireTimeoutSec,
            int evictInBackgroundSec
    ) {}
}