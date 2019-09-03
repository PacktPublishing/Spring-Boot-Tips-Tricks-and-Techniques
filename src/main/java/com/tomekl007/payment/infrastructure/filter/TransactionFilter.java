package com.tomekl007.payment.infrastructure.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.tomekl007.payment.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;



public class TransactionFilter implements Filter {

	private final static Logger LOG = LoggerFactory.getLogger(TransactionFilter.class);

	@Autowired
	private TransactionService transactionService;

	@Override
	public void init(final FilterConfig filterConfig) {
		LOG.info("Initializing filter :{}", this);
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		transactionService.startTransaction(req);
		chain.doFilter(request, response);
		transactionService.commitTransaction(req);

	}

	@Override
	public void destroy() {
		LOG.warn("Destructing filter :{}", this);
	}
}
