package com.tomekl007.chapter_3.api;

import com.tomekl007.chapter_3.domain.PaymentDto;
import com.tomekl007.chapter_3.persistance.ReactivePaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.metrics.instrument.DistributionSummary;
import org.springframework.metrics.instrument.MeterRegistry;
import org.springframework.metrics.instrument.Timer;
import org.springframework.metrics.instrument.stats.hist.NormalHistogram;
import org.springframework.metrics.instrument.stats.quantile.GKQuantiles;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController()
public class PaymentController {

  private final ReactivePaymentService paymentRepository;
  private final DistributionSummary distributionSummary;
  private final Timer postTimer;

  @Autowired
  public PaymentController(ReactivePaymentService paymentRepository,
                           MeterRegistry meterRegistry) {
    this.paymentRepository = paymentRepository;
    postTimer = meterRegistry
        .timerBuilder("payment_create")
        .quantiles(GKQuantiles.quantiles(0.99, 0.90, 0.80, 0.50).create()).create();

    distributionSummary = meterRegistry.summaryBuilder("user_id_length")
        .histogram(
            new NormalHistogram(NormalHistogram.linear(0, 5, 10))
        )
        .create();
  }

  @GetMapping("/reactive/payment/{userId}")
  public Mono<List<PaymentDto>> getPayments(@PathVariable final String userId) {
    distributionSummary.record(userId.length());
    return paymentRepository.getPayments(userId);
  }

  @PostMapping(value = "/reactive/payment", consumes = MediaType.APPLICATION_JSON)
  public Mono<PaymentDto> addPayment(@RequestBody PaymentDto payment) {
    return postTimer.record(
        () -> paymentRepository.addPayments(payment)
    );
  }

}
