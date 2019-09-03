package com.tomekl007.chapter_1;

import com.tomekl007.payment.api.PaymentService;
import com.tomekl007.chapter_3.domain.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConstructorInjection {


  private final PaymentService paymentService;

  @Autowired
  private ConstructorInjection(PaymentService paymentService) {
    this.paymentService = paymentService;
    paymentService.pay(new Payment());//we can do it
  }

}

