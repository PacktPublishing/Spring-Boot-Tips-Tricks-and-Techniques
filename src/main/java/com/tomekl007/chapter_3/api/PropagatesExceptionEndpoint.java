package com.tomekl007.chapter_3.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.NotFoundException;

@RestController
public class PropagatesExceptionEndpoint {

  // Not REST friendly: exception is propagated to the REST caller
  // That information should be internal to REST Service
  @GetMapping("/entity/{userId}")
  public String getEntity(@PathVariable final String userId) {
    return getByUser(userId);

  }

  private String getByUser(String userId) {
    // Simulate error
    throw new NotFoundException("Cannot get user by userId:" + userId);
  }
}
