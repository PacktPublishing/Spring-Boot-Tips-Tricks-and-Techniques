package com.tomekl007.chapter_2;

import com.tomekl007.chapter_2.profilesconfig.DataSourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SpringProfilesTest {

  private final DataSourceConfig dataSourceConfig;

  @Autowired
  public SpringProfilesTest(DataSourceConfig dataSourceConfig) {
    this.dataSourceConfig = dataSourceConfig;
  }

  @PostConstruct
  public void setupDatasource() {
    dataSourceConfig.setup();
  }
}