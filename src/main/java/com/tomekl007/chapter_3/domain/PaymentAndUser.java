package com.tomekl007.chapter_3.domain;

import java.util.List;

public class PaymentAndUser {
  private final User user;
  private final List<Payment> payments;

  public PaymentAndUser(User user, List<Payment> payments) {

    this.user = user;
    this.payments = payments;
  }

  public User getUser() {
    return user;
  }

  public List<Payment> getPayments() {
    return payments;
  }

  @Override
  public String toString() {
    return "PaymentAndUser{" +
        "user=" + user +
        ", payments=" + payments +
        '}';
  }
}


