package com.tomekl007.chapter_5;

import com.tomekl007.chapter_3.domain.PaymentDto;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("integration")
public class ReactivePaymentServiceLiveTest {

    @Ignore
    @Test
    public void shouldCreateAndRetrievePayment() {
        //given
        RestTemplate restTemplate = new RestTemplate();
        PaymentDto paymentDto = new PaymentDto(UUID.randomUUID().toString(),
            "123", "456", 200L);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PaymentDto> entity = new HttpEntity<>(paymentDto, httpHeaders);

        //when
        ResponseEntity<PaymentDto> response = restTemplate
                .postForEntity("http://localhost:8080/reactive/payment",
                    entity, PaymentDto.class);

        //then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();

        //when
        List<PaymentDto> getResponse = restTemplate.exchange(
            "http://localhost:8080/reactive/payment/" +
                response.getBody().getUserId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PaymentDto>>() {
                }).getBody();

        //then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(getResponse.size()).isGreaterThan(0);

    }

}