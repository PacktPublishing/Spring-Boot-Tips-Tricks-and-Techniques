package com.tomekl007.metrics;

import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.metrics.instrument.Meter;
import org.springframework.metrics.instrument.MeterRegistry;
import org.springframework.metrics.instrument.prometheus.PrometheusMeterRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/custom/metrics")
public final class MetrricsCustomController {

  private static final Logger LOGGER = LoggerFactory.getLogger(MetrricsCustomController.class);

  @Autowired
  private MeterRegistry registry;

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public List<Object> getMetrics() {
    LOGGER.info("/metrics [GET]");
    return registry
        .getMeters()
        .stream()
        .map((Function<Meter, Object>)
            Meter::measure)
        .collect(Collectors.toList());
  }
}
