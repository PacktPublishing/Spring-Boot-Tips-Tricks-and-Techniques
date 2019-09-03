package com.tomekl007.chapter_6.retry;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RetryTemplateConfiguration {

  @Bean
  public RetryTemplate retryTemplate(DefaultListenerSupport defaultListenerSupport) {
    RetryTemplate retryTemplate = new RetryTemplate();
    retryTemplate.registerListener(defaultListenerSupport);
    FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
    fixedBackOffPolicy.setBackOffPeriod(2000L);
    retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

    SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
    retryPolicy.setMaxAttempts(2);
    retryTemplate.setRetryPolicy(retryPolicy);


    return retryTemplate;
  }

}
