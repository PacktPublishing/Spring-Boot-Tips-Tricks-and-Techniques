package com.tomekl007.chapter_6;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@Configuration
public class RestTemplateConfiguration {
  public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager(int maxTotal, int maxPerRoute) {
    PoolingHttpClientConnectionManager result = new PoolingHttpClientConnectionManager();
    result.setMaxTotal(maxTotal);
    result.setDefaultMaxPerRoute(maxPerRoute);
    return result;
  }

  public RequestConfig requestConfig(int connectionTimeout, int socketTimeout) {
    RequestConfig result = RequestConfig.custom()
        .setConnectionRequestTimeout(1000)//how long wait for a connection from connection manager
        .setConnectTimeout(connectionTimeout)//how long wait for establishing connection
        .setSocketTimeout(socketTimeout)//how long wait between packets
        .build();
    return result;
  }
  public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager, RequestConfig requestConfig) {
    CloseableHttpClient result = HttpClientBuilder
        .create()
        .setConnectionManager(poolingHttpClientConnectionManager)
        .setDefaultRequestConfig(requestConfig)
        .build();
    return result;
  }

  @Bean("payments-client")
  public RestTemplate restTemplateCountries() {
    PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = poolingHttpClientConnectionManager(20, 20);
    RequestConfig requestConfig = requestConfig(2000, 1400);
    CloseableHttpClient httpClient = httpClient(poolingHttpClientConnectionManager, requestConfig);
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    requestFactory.setHttpClient(httpClient);
    return new RestTemplate(requestFactory);
  }

  @Bean("facebook-client")
  public RestTemplate restTemplateFacebook() {
    PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = poolingHttpClientConnectionManager(20, 20);
    RequestConfig requestConfig = requestConfig(1000, 700);
    CloseableHttpClient httpClient = httpClient(poolingHttpClientConnectionManager, requestConfig);
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    requestFactory.setHttpClient(httpClient);
    return new RestTemplate(requestFactory);
  }
}
