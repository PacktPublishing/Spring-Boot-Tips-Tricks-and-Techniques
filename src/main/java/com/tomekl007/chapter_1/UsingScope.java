package com.tomekl007.chapter_1;

import com.tomekl007.chapter_1.beans.PrototypeBean;
import com.tomekl007.chapter_1.beans.SingletonBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsingScope {
  private final SingletonBean singletonBean;
  private final PrototypeBean prototypeBean;


  @Autowired
  public UsingScope(SingletonBean singletonBean, PrototypeBean prototypeBean) {
    System.out.println("creating UsingScope");
    this.singletonBean = singletonBean;
    this.prototypeBean = prototypeBean;
  }
}
