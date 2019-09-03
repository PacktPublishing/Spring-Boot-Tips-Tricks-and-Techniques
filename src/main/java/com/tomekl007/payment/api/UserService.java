package com.tomekl007.payment.api;

import com.tomekl007.chapter_3.domain.PaymentAndUser;
import com.tomekl007.chapter_3.domain.User;

import java.util.List;
import java.util.Optional;


public interface UserService {
  List<User> getAllUsers();
  void insert(User user);
  Optional<PaymentAndUser> getPaymentAndUsersForUserId(String userId);
}
