package com.tomekl007.eventbus.api;

import com.tomekl007.eventbus.domain.Event;

public interface EventBus {
  void publish(Event event);
  Event receive();
}
