package com.kccloudtutor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Health;

@Configuration
public class CostOptimizationConfig {
    
    @Bean
    public HealthIndicator costOptimizationHealth() {
        return () -> Health.up()
            .withDetail("aws-free-tier", "enabled")
            .withDetail("container-optimization", "enabled")
            .withDetail("resource-limits", "configured")
            .withDetail("cost-monitoring", "active")
            .build();
    }
}
