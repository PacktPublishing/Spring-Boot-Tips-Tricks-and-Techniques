package com.tomekl007.notifications.api;

import com.tomekl007.notifications.domain.HelloMessage;
import com.tomekl007.notifications.domain.PaymentAddedNotification;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {


    @MessageMapping("/payment-add")
    @SendTo("/topic/greetings")
    public HelloMessage welcomeUser(PaymentAddedNotification paymentAddedNotification) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new HelloMessage("Hello, " + paymentAddedNotification.getName() + "!");
    }

}