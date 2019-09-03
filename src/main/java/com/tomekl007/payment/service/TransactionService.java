package com.tomekl007.payment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

public class TransactionService {
  private final static Logger LOG = LoggerFactory.getLogger(TransactionService.class);


  public void startTransaction(HttpServletRequest req) {
    LOG.info("Starting Transaction for req :{}", req.getRequestURI());
  }

  public void commitTransaction(HttpServletRequest req) {
    LOG.info("Committing Transaction for req :{}", req.getRequestURI());
  }
}
