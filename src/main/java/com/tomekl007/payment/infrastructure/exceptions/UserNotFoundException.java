package com.tomekl007.payment.infrastructure.exceptions;

public class UserNotFoundException extends Throwable {
  public UserNotFoundException(String msg) {
    super(msg);
  }
}
