package com.tomekl007.chapter_7;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;
import org.springframework.metrics.instrument.Measurement;
import org.springframework.metrics.instrument.Meter;
import org.springframework.metrics.instrument.simple.SimpleMeterRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class MicroMeterCacheSize {
  private SimpleMeterRegistry metricRegistry = new SimpleMeterRegistry();

  private LoadingCache<String, String> cache =
      CacheBuilder
          .newBuilder()
          .maximumSize(10)
          .build(
              new CacheLoader<String, String>() {
                @Override
                public String load(String key) {
                  return key.toUpperCase();
                }
              });

  @Test
  public void shouldUseGauge() throws ExecutionException {
    //given
    metricRegistry.gauge("cache.size", cache, value -> cache.size());

    //when
    cache.get("k");
    cache.get("k2");
    cache.get("k3");

    //then
    Optional<Meter> gauge = metricRegistry.findMeter(
        Meter.Type.Gauge, "cache.size");
    List<Measurement> measurements = new ArrayList<>();
    gauge.get().measure().iterator().forEachRemaining(measurements::add);
    assertThat(measurements.get(0).getValue()).isEqualTo(3.0);

  }
}
