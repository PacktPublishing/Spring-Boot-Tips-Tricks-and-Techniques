package com.tomekl007.payment.infrastructure.healtchecks;

import com.tomekl007.payment.details.api.PaymentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DownstreamHealthcheck implements HealthIndicator {

  @Autowired
  private PaymentDetails paymentDetails;

  @Override
  public Health health() {
    try {
      //try some non existing
      String response = paymentDetails.getInfoAboutPayment("INIT-COMPANY");
      if (response.contains("is not currently not available")) {
        return Health.down().build();
      }
      return Health.up().build();
    } catch (Exception ex) {
      return Health.down(ex).build();
    }
  }
}
