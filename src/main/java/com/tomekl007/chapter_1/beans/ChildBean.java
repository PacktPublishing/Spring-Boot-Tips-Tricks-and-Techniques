package com.tomekl007.chapter_1.beans;

import org.springframework.stereotype.Component;

@Component
public class ChildBean extends ParentBean {
  // we need to worry about lifecycle of ParentBean and ChildBean

  @Override
  public void action() {
    System.out.println("Action from ChildBean");
  }
}
