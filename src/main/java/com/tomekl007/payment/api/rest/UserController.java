package com.tomekl007.payment.api.rest;

import com.tomekl007.payment.api.UserService;
import com.tomekl007.chapter_3.domain.PaymentAndUser;
import com.tomekl007.chapter_3.domain.User;
import com.tomekl007.chapter_3.domain.UserDto;
import com.tomekl007.payment.infrastructure.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/users")
public class UserController {
  private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

  private final UserService userService;

  @PostConstruct
  public void insertUsers() {
    userService.insert(new User("T1", "m@m.pl"));
    userService.insert(new User("T2", "m2@m.pl"));
    userService.insert(new User("T3", "m3@m.pl"));
  }

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }


  @GetMapping(value = "", produces = "application/json")
  public List<UserDto> getAllUsers() {
    LOG.info("Fetching all the users");
    return userService.getAllUsers().stream().map(
        u -> new UserDto(u.getId(), u.getName(), u.getEmail())
    ).collect(Collectors.toList());
  }

  @GetMapping(value = "payments-for-user/{userId}", produces = "application/json")
  public PaymentAndUser paymentAndUsers(@PathVariable final String userId) throws UserNotFoundException {
    Optional<PaymentAndUser> paymentAndUsersForUserId = userService.getPaymentAndUsersForUserId(userId);
    if (!paymentAndUsersForUserId.isPresent()) {
      throw new UserNotFoundException("Payments for user id: " + userId + " not found");
    } else {
      return paymentAndUsersForUserId.get();
    }

  }


}
