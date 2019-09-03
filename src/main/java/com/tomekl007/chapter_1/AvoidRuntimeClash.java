package com.tomekl007.chapter_1;

import com.tomekl007.chapter_1.beans.ExternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class AvoidRuntimeClash {

  private final ExternalService externalService;

  //will fail with:
  //Parameter 0 of constructor in com.tomekl007.chapter_1.AvoidRuntimeClash required a single bean, but 2 were found:
//  @Autowired
//  public AvoidRuntimeClash(@ExternalService externalService) {
//    this.externalService = externalService;
//    externalService.call();
//  }

  @Autowired
  public AvoidRuntimeClash(@Qualifier("RESTExternalService") ExternalService externalService) {
    this.externalService = externalService;
    externalService.call();
  }
}
