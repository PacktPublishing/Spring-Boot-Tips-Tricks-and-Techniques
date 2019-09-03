package com.tomekl007.payment.infrastructure.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

public class InterceptRequestResponseFilter implements Filter {

	private final static Logger LOG = LoggerFactory.getLogger(InterceptRequestResponseFilter.class);

	@Override
	public void init(final FilterConfig filterConfig) {
		LOG.info("Initializing filter :{}", this);
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		logInfoAboutRequestAndResponse(request, response, chain, req, res);

	}

	private void logInfoAboutRequestAndResponse(ServletRequest request, ServletResponse response, FilterChain chain, HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		LOG.info("Logging Request  {} : {}", req.getMethod(), req.getRequestURI());
		chain.doFilter(request, response);
		LOG.info("Logging Response :{}", res.getContentType());
	}

	@Override
	public void destroy() {
		LOG.warn("Destructing filter :{}", this);
	}
}
