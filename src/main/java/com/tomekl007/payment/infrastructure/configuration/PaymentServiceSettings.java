package com.tomekl007.payment.infrastructure.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "payment-config")
public class PaymentServiceSettings {

    private List<String> supportedAccounts;

    public List<String> getSupportedAccounts() {
        return supportedAccounts;
    }

    public void setSupportedAccounts(List<String> supportedAccounts) {
        this.supportedAccounts = supportedAccounts;
    }
}
