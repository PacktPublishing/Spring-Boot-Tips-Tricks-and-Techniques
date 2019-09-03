package com.tomekl007.chapter_3.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.NotFoundException;

@RestController()
public class RESTErrorHandlingController {


  @GetMapping("/entity-two/{userId}")
  public ResponseEntity<String> getEntity(@PathVariable final String userId) {
    try {
      return ResponseEntity.ok(getByUser(userId));
    } catch (NotFoundException ex) {
      return ResponseEntity.notFound().build(); //will map to proper REST error code
    }
  }

  private String getByUser(String userId) {
    // Simulate error
    throw new NotFoundException("Cannot get user by userId:" + userId);
  }

}
