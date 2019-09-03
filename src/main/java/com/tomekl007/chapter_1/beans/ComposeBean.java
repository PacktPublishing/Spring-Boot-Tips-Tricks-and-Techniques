package com.tomekl007.chapter_1.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComposeBean {
  // Spring is taking care of lifecycle of all components
  // We don't need to worry about initialization order;
  private final ParentBean parentBean;

  @Autowired
  public ComposeBean(ParentBean parentBean) {
    this.parentBean = parentBean;
  }
}
