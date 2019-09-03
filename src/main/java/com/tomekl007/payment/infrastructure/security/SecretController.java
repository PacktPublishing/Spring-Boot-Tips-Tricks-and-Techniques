package com.tomekl007.payment.infrastructure.security;

import com.tomekl007.chapter_3.persistance.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecretController {

    @Autowired
    private PaymentRepository paymentRepository;

    @RequestMapping("/secret")
    public Long numberOfPayment() {
        return paymentRepository.count();
    }
}
