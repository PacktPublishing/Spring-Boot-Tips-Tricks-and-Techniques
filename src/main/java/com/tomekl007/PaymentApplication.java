package com.tomekl007;

import com.tomekl007.payment.api.PaymentService;
import io.prometheus.client.CollectorRegistry;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.metrics.export.prometheus.EnablePrometheusMetrics;
import org.springframework.metrics.instrument.prometheus.PrometheusMeterRegistry;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableHystrix
@EnablePrometheusMetrics
public class PaymentApplication {
    @Autowired(required = false)
    PaymentService paymentService;

    public static void main(String[] args) {
        //you can use -Dspring.profiles.active=dev when starting app instead of hardcoding it
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "dev");

        SpringApplication.run(PaymentApplication.class, args);
    }


    //this is instead of XML based config
    @Bean
    @ConditionalOnMissingBean
    CollectorRegistry collectorRegistry() {
        CollectorRegistry collectorRegistry = new CollectorRegistry();
        return collectorRegistry;
    }

    @Bean
    @Primary
    PrometheusMeterRegistry meterRegistry() {
        return new PrometheusMeterRegistry(collectorRegistry());
    }

    static {
        //HACK Avoids duplicate metrics registration in case of Spring Boot dev-tools restarts
        CollectorRegistry.defaultRegistry.clear();
    }

}
