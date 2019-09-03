package com.tomekl007.chapter_6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FacebookService {

  private RestTemplate restTemplate;

  @Autowired
  public FacebookService(@Qualifier("facebook-client") RestTemplate restTemplate) {

    this.restTemplate = restTemplate;
  }

  public void simpleGet() {
    restTemplate.getForEntity("http://facebook.com", String.class);
  }
}
