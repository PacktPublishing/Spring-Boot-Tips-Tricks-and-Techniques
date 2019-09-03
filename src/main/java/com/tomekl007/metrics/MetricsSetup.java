package com.tomekl007.metrics;

import io.prometheus.client.CollectorRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.metrics.instrument.Clock;
import org.springframework.metrics.instrument.MeterRegistry;
import org.springframework.metrics.instrument.prometheus.PrometheusMeterRegistry;

//@Configuration
public class MetricsSetup {
  @Bean
  public Clock micrometerClock() {
    return Clock.SYSTEM;
  }

  @Bean
  public MeterRegistry prometheusMeterRegistry(CollectorRegistry collectorRegistry,
                                               Clock clock) {
    return new PrometheusMeterRegistry(collectorRegistry, clock);
  }

  @Bean
  public CollectorRegistry collectorRegistry() {
    return new CollectorRegistry(true);
  }

}
