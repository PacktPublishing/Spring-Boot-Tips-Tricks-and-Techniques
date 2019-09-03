package com.tomekl007.chapter_2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpecificService {
  private final SpecificServiceSettings specificServiceSettings;

  @Autowired
  // Inject fine-grained config for this specific service - easy to "mock" and test.
  public SpecificService(SpecificServiceSettings specificServiceSettings) {
    this.specificServiceSettings = specificServiceSettings;
  }
}
