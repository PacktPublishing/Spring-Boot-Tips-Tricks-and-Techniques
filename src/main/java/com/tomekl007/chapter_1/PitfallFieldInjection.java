package com.tomekl007.chapter_1;


import com.tomekl007.payment.api.PaymentService;
import com.tomekl007.chapter_3.domain.Payment;
import org.springframework.beans.factory.annotation.Autowired;

//@Component
public class PitfallFieldInjection {

  @Autowired
  private PaymentService paymentService;

  private PitfallFieldInjection(){
    paymentService.pay(new Payment());//problem!!
  }

}
