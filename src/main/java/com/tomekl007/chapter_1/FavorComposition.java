package com.tomekl007.chapter_1;

import com.tomekl007.chapter_1.beans.ChildBean;
import com.tomekl007.chapter_1.beans.ComposeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FavorComposition {

  private final ChildBean childBean;
  private final ComposeBean composeBean;

  @Autowired
  public FavorComposition(ChildBean childBean, ComposeBean composeBean) {
    this.childBean = childBean;
    this.composeBean = composeBean;
  }
}
