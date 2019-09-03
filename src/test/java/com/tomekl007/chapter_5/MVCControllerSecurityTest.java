package com.tomekl007.chapter_5;

import com.tomekl007.eventbus.api.EventBus;
import com.tomekl007.PaymentApplication;
import com.tomekl007.payment.api.MVCController;
import com.tomekl007.chapter_3.domain.Payment;
import com.tomekl007.chapter_3.domain.PaymentDto;
import com.tomekl007.chapter_3.persistance.PaymentRepository;
import com.tomekl007.payment.infrastructure.security.SpringSecurityWebAppConfigWithCsrf;
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

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {PaymentApplication.class, SpringSecurityWebAppConfigWithCsrf.class})
@WebMvcTest(
    controllers = MVCController.class
)
public class MVCControllerSecurityTest {

  @MockBean
  private PaymentRepository paymentRepository;

  @MockBean
  private EventBus eventBus;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldReturnAllAvailablePayments() throws Exception {
    //given
    Iterable<Payment> payments = Arrays.asList(
        new Payment("u1", "134",
            "5125", 23L));
    when(paymentRepository.findAll()).thenReturn(payments);

    //when, then
    this.mockMvc.perform(get("/mvc/all-payments")).andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("u1")))
        .andExpect(content().string(containsString("134")))
        .andExpect(content().string(containsString("5125")))
        .andReturn();
  }

  @Test
  public void shouldReturnFormForCreatingPaymentWithCsrfHiddenField() throws Exception {
    //when, then
    this.mockMvc.perform(get("/mvc/createPayment"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("_csrf"))) //included!
        .andReturn();
  }

  @Test
  public void shouldPostNewPaymentWithCsrf() throws Exception {
    //given
    PaymentDto paymentDto = new PaymentDto();
    paymentDto.setUserId("u_id1");
    paymentDto.setAccountFrom("123");
    paymentDto.setAccountTo("5325");

    mockMvc.perform(
        post("/mvc/payment")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .with(csrf())
            .sessionAttr("paymentDto", paymentDto)
    )
        .andExpect(status().isOk())
        .andExpect(view().name("allPayments"));
  }

  @Test
  public void shouldReturn403IfPostWithoutCsrf() throws Exception {
    //given
    PaymentDto paymentDto = new PaymentDto();
    paymentDto.setUserId("u_id1");
    paymentDto.setAccountFrom("4214");
    paymentDto.setAccountTo("531251");

    mockMvc.perform(
        post("/mvc/payment")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            //note that req send without
            .sessionAttr("paymentDto", paymentDto)
    )
        .andExpect(status().is(403));
  }
}