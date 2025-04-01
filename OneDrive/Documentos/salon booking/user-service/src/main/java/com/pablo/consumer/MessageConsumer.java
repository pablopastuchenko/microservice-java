package com.pablo.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    @RabbitListener(queues = "user service")
    public void receiveMessage(String message) {
        System.out.println("Mensagem recebida: " + message);
    }
}
