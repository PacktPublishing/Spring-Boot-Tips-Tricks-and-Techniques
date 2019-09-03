package com.tomekl007.payment.details.api;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.metrics.annotation.Timed;
import org.springframework.metrics.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;
import java.util.concurrent.TimeUnit;

@RestController
public class PaymentDetailsController {
  private static Logger log = Logger.getLogger(PaymentDetailsController.class);

  private PaymentDetails paymentDetails;

  private LoadingCache<String, String> loader =
      CacheBuilder
          .newBuilder()
          .maximumSize(100) //on prod should be higher
          .expireAfterWrite(10, TimeUnit.SECONDS)
          .removalListener((RemovalListener<String, String>) removalNotification -> {
            log.info("entry from cache expired: " + removalNotification);
          })
          .build(
              new CacheLoader<String, String>() {
                @Override
                public String load(String key) {
                  return paymentDetails.getInfoAboutPayment(key);
                }
              });

  @Autowired
  public PaymentDetailsController(
      PaymentDetails paymentDetails,
      MeterRegistry meterRegistry) {
    this.paymentDetails = paymentDetails;
    meterRegistry.gauge("cache_size", loader, v -> loader.size());

  }

  @Timed
  @RequestMapping(value = "/payment/payment-details/{account}", produces = MediaType.APPLICATION_JSON)
  String getInfoAboutPayment(@PathVariable final String account) throws Exception {
    return loader.get(account);
  }
}