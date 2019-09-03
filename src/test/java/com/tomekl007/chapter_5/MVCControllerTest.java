package com.tomekl007.chapter_5;

import com.tomekl007.eventbus.api.EventBus;
import com.tomekl007.PaymentApplication;
import com.tomekl007.chapter_3.domain.Payment;
import com.tomekl007.chapter_3.domain.PaymentDto;
import com.tomekl007.chapter_3.persistance.PaymentRepository;
import com.tomekl007.payment.api.MVCController;
import com.tomekl007.payment.infrastructure.security.SpringSecurityWebAppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {PaymentApplication.class, SpringSecurityWebAppConfig.class})
@WebMvcTest(
    controllers = MVCController.class
)
public class MVCControllerTest {

  @MockBean
  private PaymentRepository paymentRepository;

  @MockBean
  private EventBus eventBus;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldReturnAllPayments() throws Exception {
    //given
    Iterable<Payment> payments = Arrays.asList(
        new Payment("u1", "from1",
            "to1", 23L));
    when(paymentRepository.findAll()).thenReturn(payments);

    //when, then
    this.mockMvc.perform(get("/mvc/all-payments")).andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("u1")))
        .andExpect(content().string(containsString("from1")))
        .andExpect(content().string(containsString("to1")))
        .andReturn();
  }

  @Test
  public void shouldReturnFormForCreatingPayment() throws Exception {
    //when, then
    this.mockMvc.perform(get("/mvc/createPayment")).andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(
            "<form action=\"/mvc/payment\" method=\"post\">\n" +
                "    <p>userId: <input type=\"text\" id=\"userId\" name=\"userId\" value=\"\"/></p>\n" +
                "    <p>accountFrom: <input type=\"text\" id=\"accountFrom\" name=\"accountFrom\" value=\"\"/></p>\n" +
                "    <p>accountTo: <input type=\"text\" id=\"accountTo\" name=\"accountTo\" value=\"\"/></p>\n" +
                "    <p>amount: <input type=\"number\" id=\"amount\" name=\"amount\" value=\"\"/></p>\n" +
                "    <p><input type=\"submit\" value=\"Submit\"/> <input type=\"reset\" value=\"Reset\"/></p>\n" +
                "</form>")))

        .andReturn();
  }

  @Test
  public void shouldPostNewPayment() throws Exception {
    //given
    PaymentDto paymentDto = new PaymentDto();
    paymentDto.setUserId("u_id1");
    paymentDto.setAccountFrom("51351");
    paymentDto.setAccountTo("123");

    mockMvc.perform(
        post("/mvc/payment")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .sessionAttr("paymentDto", paymentDto)
    )
        .andExpect(status().isOk())
        .andExpect(view().name("allPayments"));
  }
}