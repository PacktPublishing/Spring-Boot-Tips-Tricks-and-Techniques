package com.tomekl007.chapter_2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ConfigInheritance {
  private final static Logger LOG = LoggerFactory.getLogger(ConfigInheritance.class);
  private final SpecificServiceSettings specificServiceSettings;

  @Autowired
  public ConfigInheritance(SpecificServiceSettings specificServiceSettings) {
    this.specificServiceSettings = specificServiceSettings;
  }
  @PostConstruct
  public void logSettings(){
    LOG.info("starting ConfigInheritance, current config is: {}", specificServiceSettings);
    // Validate behaviour when starting with -Dspring.profiles.active=prod
  }
}
