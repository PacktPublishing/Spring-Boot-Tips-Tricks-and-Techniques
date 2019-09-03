package com.tomekl007.chapter_2;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "service-config")
public class SpecificServiceSettings {
  private Integer a;
  private String b;

  public Integer getA() {
    return a;
  }

  public void setA(Integer a) {
    this.a = a;
  }

  public String getB() {
    return b;
  }

  public void setB(String b) {
    this.b = b;
  }

  @Override
  public String
  toString() {
    return "SpecificServiceSettings{" +
        "a=" + a +
        ", b='" + b + '\'' +
        '}';
  }
}
