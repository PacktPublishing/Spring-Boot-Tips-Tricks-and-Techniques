package com.tomekl007.chapter_5;

import com.tomekl007.chapter_3.domain.PaymentDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
public class ReactivePaymentServiceIntegrationTest {

  // Inject which port we was assigned
  @Value("${local.server.port}")
  private int port;


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
        .postForEntity(createTestUrl("/reactive/payment"),
            entity, PaymentDto.class);

    //then
    assertThat(response.getStatusCode().value()).isEqualTo(200);
    assertThat(response.getBody()).isNotNull();

    //when
    List<PaymentDto> getResponse = restTemplate.exchange(
        createTestUrl("/reactive/payment/" +
            response.getBody().getUserId()),
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<PaymentDto>>() {
        }).getBody();

    //then
    assertThat(response.getStatusCode().value()).isEqualTo(200);
    assertThat(getResponse.size()).isGreaterThan(0);

  }

  private String createTestUrl(String suffix) {
    return "http://localhost:" + port + suffix;
  }

}