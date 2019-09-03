package com.tomekl007.chapter_1.beans;


import org.springframework.stereotype.Component;

@Component
public class RESTExternalService implements ExternalService {
  @Override
  public void call() {
    System.out.println("call RESTExternalService");
  }
}
