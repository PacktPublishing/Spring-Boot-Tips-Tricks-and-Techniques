package com.tomekl007.chapter_2.profilesconfig;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("integration")
public class DefaultDataSourceConfig implements DataSourceConfig {
  @Override
  public void setup() {
    System.out.println("Setting up dataSource for INTEGRATION environment. ");
  }
}
