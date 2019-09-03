package com.tomekl007.chapter_7;

import org.junit.Test;
import org.springframework.metrics.instrument.DistributionSummary;
import org.springframework.metrics.instrument.Measurement;
import org.springframework.metrics.instrument.Meter;
import org.springframework.metrics.instrument.MeterRegistry;
import org.springframework.metrics.instrument.prometheus.PrometheusMeterRegistry;
import org.springframework.metrics.instrument.simple.SimpleMeterRegistry;
import org.springframework.metrics.instrument.stats.hist.NormalHistogram;
import org.springframework.metrics.instrument.stats.quantile.WindowSketchQuantiles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ResponseTimesHistogramTest {
  public static final int NORMAL_SIZE = 20;
  public static final int OUTLIER_SIZE = 100;

  @Test
  public void shouldMeasureActionsBuildingHistogram() {
    //given
    SimpleMeterRegistry metricRegistry = new SimpleMeterRegistry();
    DistributionSummary distributionSummary = metricRegistry
        .summaryBuilder("request-simple.size")
        .create();

    //when
    for (int i = 0; i < 100; i++) {
      distributionSummary.record(NORMAL_SIZE);
    }
    //one outlier
    distributionSummary.record(OUTLIER_SIZE);


    //then

    assertThat(distributionSummary.count()).isEqualTo(101);
    assertThat(distributionSummary.totalAmount()).isEqualTo(2100);
  }

  @Test
  public void shouldMeasureActionsBuildingHistogramWithPercentiles() {
    //given
    MeterRegistry metricRegistry = new PrometheusMeterRegistry();
    DistributionSummary distributionSummary = metricRegistry
        .summaryBuilder("request-percentiles.size")
        .quantiles(
            WindowSketchQuantiles
            .quantiles(0.3, 0.5, 0.99)
            .create()
        )
        .create();

    //when
    for (int i = 0; i < 100; i++) {
      distributionSummary.record(NORMAL_SIZE);
    }
    //one outlier
    distributionSummary.record(OUTLIER_SIZE);


    //then
    List<Measurement> measurements = new ArrayList<>();
    metricRegistry.findMeter(Meter.Type.DistributionSummary,
        "request-percentiles.size")
        .get().measure().iterator().forEachRemaining(measurements::add);

    List<String> result =
        extractPercentilesAsMap(measurements,"request-percentiles.size");
    assertThat(result)
        .contains("0.3=20.0", "0.5=20.0", "0.99=100.0");

  }

  @Test
  public void shouldMeasureActionsBuildingHistogramWithLinear() {
    //given
    MeterRegistry metricRegistry = new PrometheusMeterRegistry();
    DistributionSummary distributionSummary = metricRegistry
        .summaryBuilder("request-summary.size")
        .histogram(
            new NormalHistogram(NormalHistogram
                .linear(0, 10, 5))
        )
        .create();

    //when
    distributionSummary.record(1);
    distributionSummary.record(2);
    for (int i = 0; i < 100; i++) {
      distributionSummary.record(NORMAL_SIZE);
    }
    //one outlier
    distributionSummary.record(OUTLIER_SIZE);


    //then
    List<Measurement> measurements = new ArrayList<>();
    metricRegistry.findMeter(Meter.Type.DistributionSummary, "request-summary.size")
        .get().measure().iterator().forEachRemaining(measurements::add);

    List<String> result = extractPercentilesAsMap(measurements, "request-summary.size_bucket");
    assertThat(result)
        .contains("10.0=2.0", "20.0=100.0", "+Inf=1.0");

  }

  //continue from here https://www.baeldung.com/micrometer

  private List<String> extractPercentilesAsMap(List<Measurement> measurements, String name) {
    List<String> result = measurements
        .stream()
        .filter(m -> m.getName().equals(name))
        .map(m -> m.getTags().first().getValue() + "=" + m.getValue()).collect(Collectors.toList());
    ;
    return result;
  }


}
